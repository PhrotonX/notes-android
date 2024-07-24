package com.phroton.notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewModel;
import com.phroton.notes.R;
import com.phroton.notes.RequestCode;
import com.phroton.notes.ui.editor.EditorActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class NoteFragment extends Fragment {
    protected ActivityResultLauncher<Intent> mActivityResultContract;
    protected Context mContext;
    protected NoteViewAdapter mNoteViewAdapter;
    protected int mFlags;
    protected LifecycleOwner mLifecycleOwner;
    private NoteViewAdapter.OnClickListener mListener;
    protected NoteViewModel mNoteViewModel;

    public ActivityResultLauncher<Intent> getActivityResultContract(){
        return mActivityResultContract;
    }

    public MenuProvider getDefaultMenuProvider(){
        return new MenuProvider() {

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        };
    }

    public NoteViewAdapter getNoteViewAdapter(){
        return mNoteViewAdapter;
    }

    public NoteViewModel getNoteViewModel(){
        return mNoteViewModel;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = onInitializeView(inflater, container, savedInstanceState);

        mContext = getContext();
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mLifecycleOwner = getViewLifecycleOwner();

        RecyclerView notesView = (RecyclerView)root.findViewById(R.id.notesList);
        notesView.setLayoutManager(new LinearLayoutManager(mContext));
        mNoteViewAdapter = new NoteViewAdapter(mContext, mFlags);

        notesView.setAdapter(mNoteViewAdapter);

        mNoteViewAdapter.setOnClickListener(onItemClick());

        //Original NoteVIewModel initialization code...

        LiveData<List<Note>> allNotes = mNoteViewModel.getNotesCompat();

        if(allNotes != null){
            allNotes.observe(mLifecycleOwner, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    if(notes != null){
                        mNoteViewAdapter.setNotes(notes);
                        mNoteViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }else{
            Toast.makeText(mContext, R.string.database_read_error, Toast.LENGTH_SHORT).show();
            List<Note> sampleNote = new ArrayList<>();
            sampleNote.add(new Note("Error 1", "Error Note 1"));
            sampleNote.add(new Note("Error 2", "Error Note 2"));
            sampleNote.add(new Note("Error 3", "Error Note 3"));

            mNoteViewAdapter.setNotes(sampleNote);
            mNoteViewAdapter.notifyDataSetChanged();
        }

        //@NOTE: Handle request after editing a note.
        mActivityResultContract = onActivityResult();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    public abstract View onInitializeView(@NonNull LayoutInflater inflater,
                                          ViewGroup container, Bundle savedInstanceState);

    public ActivityResultLauncher<Intent> onActivityResult(){
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Note note;
                int dbNoteId = result.getData().getIntExtra(Note.NOTE_ID_EXTRA, -1);
                int rvNoteId = result.getData().getIntExtra(Note.NOTE_POSITION_EXTRA, -1);
                switch(result.getResultCode()){
                    case EditorActivity.RESULT_OK:
                        note = Note.unpackCurrentNote(result.getData(), true);
                        onActivityResultOk(result, note, dbNoteId, rvNoteId);
                        break;
                    case EditorActivity.RESULT_DELETE:
                        note = Note.unpackCurrentNote(result.getData(), true);
                        onActivityResultDelete(result, note, dbNoteId, rvNoteId);
                        break;
                    case EditorActivity.RESULT_REMOVE:
                        onActivityResultRemove(result, dbNoteId, rvNoteId);
                        break;
                    case EditorActivity.RESULT_RESTORE:
                        onActivityResultRestore(result, dbNoteId, rvNoteId);
                        break;
                    case EditorActivity.RESULT_CANCELED:
                        onActivityResultCancel(result);
                        break;
                    default:
                        onActivityResultNull(result);
                        break;
                }
            }
        });
    }

    protected void onActivityResultCancel(ActivityResult result){
        Toast.makeText(getContext(), "EditorActivity: Canceled", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResultDelete(ActivityResult result, Note note, int dbNoteId, int rvNoteId){
        getNoteViewModel().delete(note);
        getNoteViewAdapter().notifyItemRemoved(note.getId());
    }

    protected void onActivityResultNull(ActivityResult result){
        Toast.makeText(getContext(), "EditorActivity: Error", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResultOk(ActivityResult result, @NonNull Note note, int dbNoteId, int rvNoteId){
        Toast.makeText(getContext(), "MainActivity noteId: " + note.getId(), Toast.LENGTH_SHORT).show();
        if(note.getId() == -1){
            Toast.makeText(getContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
        }

        getNoteViewModel().update(note);
        getNoteViewAdapter().notifyItemChanged(rvNoteId);
    }

    protected void onActivityResultRemove(ActivityResult result, int dbNoteId, int rvNoteId){
        getNoteViewModel().markAsDeleted(dbNoteId, true);
        getNoteViewAdapter().notifyItemRemoved(rvNoteId);
    }

    protected void onActivityResultRestore(ActivityResult result, int dbNoteId, int rvNoteId){
        getNoteViewModel().markAsDeleted(dbNoteId, false);
        getNoteViewAdapter().notifyItemRemoved(rvNoteId);
    }

    public NoteViewAdapter.OnClickListener onItemClick(){
        return new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int rvPosition, int dbPosition) {
                Intent intent = new Intent(requireContext(), EditorActivity.class);
                intent.putExtra(RequestCode.REQUEST_CODE, RequestCode.REQUEST_CODE_EDIT_NOTE);
                intent.putExtra(Note.NOTE_ID_EXTRA, dbPosition);
                intent.putExtra(Note.NOTE_POSITION_EXTRA, rvPosition);
                getActivityResultContract().launch(intent);
            }
        };
    }

    public void setFlags(int flags){
        mFlags = flags;
    }
}