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
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private RecyclerView mNoteRecyclerView;
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

    private void initializeNoteViewAdapter(List<Note> notes){
        mNoteViewAdapter = new NoteViewAdapter(mContext, notes, mFlags);

        mNoteRecyclerView.setAdapter(mNoteViewAdapter);

        ItemTouchHelper.SimpleCallback itemCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return onItemMove();
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int rvPosition = viewHolder.getBindingAdapterPosition();
                long dbPosition = (long)viewHolder.itemView.getTag();
                switch(direction){
                    case ItemTouchHelper.LEFT:
                        onItemSwipedLeft(viewHolder, dbPosition, rvPosition);
                        break;
                    case ItemTouchHelper.RIGHT:
                        onItemSwipedRight(viewHolder, dbPosition, rvPosition);
                        break;
                    default:
                        break;
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemCallback);
        itemTouchHelper.attachToRecyclerView(mNoteRecyclerView);

        onInitializeNoteViewAdapter();

        mNoteViewAdapter.setOnClickListener(onItemClick());
    }

    public void deleteItem(Note note, int rvPosition){
        getNoteViewModel().delete(note);
        getNoteViewAdapter().notifyItemRemoved(rvPosition);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = onInitializeView(inflater, container, savedInstanceState);

        mContext = getContext();
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mLifecycleOwner = getViewLifecycleOwner();

        mNoteRecyclerView = (RecyclerView)root.findViewById(R.id.notesList);
        mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        //Original NoteViewModel initialization code...

        LiveData<List<Note>> allNotes = onRetrieveNotes();
        if(allNotes != null){
            allNotes.observe(mLifecycleOwner, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    if(notes != null){
                        initializeNoteViewAdapter(notes);
                        //mNoteViewAdapter.setNotes(notes);
                        //mNoteViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }else{
            Toast.makeText(mContext, R.string.database_read_error, Toast.LENGTH_SHORT).show();
            List<Note> errorNotes = new ArrayList<>();
            errorNotes.add(new Note("Error 1", "Error Note 1"));
            errorNotes.add(new Note("Error 2", "Error Note 2"));
            errorNotes.add(new Note("Error 3", "Error Note 3"));

            initializeNoteViewAdapter(errorNotes);
            //mNoteViewAdapter.setNotes(sampleNote);
            //mNoteViewAdapter.notifyDataSetChanged();
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
                Intent intent = result.getData();
                if(intent == null) return;
                long dbNoteId = intent.getLongExtra(Note.NOTE_ID_EXTRA, -1);
                int rvNoteId = intent.getIntExtra(Note.NOTE_POSITION_EXTRA, -1);
                //Toast.makeText(getContext(), "onACtivityResult() DB: " + dbNoteId + " RV: " + rvNoteId, Toast.LENGTH_SHORT).show();

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
        //Toast.makeText(getContext(), "EditorActivity: Canceled", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResultDelete(ActivityResult result, Note note, long dbNoteId, int rvNoteId){
        deleteItem(note, rvNoteId);
    }

    protected void onActivityResultNull(ActivityResult result){
        Toast.makeText(getContext(), "EditorActivity: Error", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResultOk(ActivityResult result, @NonNull Note note, long dbNoteId, int rvNoteId){
        //Toast.makeText(getContext(), "MainActivity noteId: " + note.getId(), Toast.LENGTH_SHORT).show();
        if(note.getId() == -1){
            Toast.makeText(getContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
        }

        getNoteViewModel().update(note);
        //getNoteViewAdapter().notifyItemChanged(rvNoteId);
    }

    protected void onActivityResultRemove(ActivityResult result, long dbNoteId, int rvNoteId){
        removeItem(dbNoteId, rvNoteId);
    }

    protected void onActivityResultRestore(ActivityResult result, long dbNoteId, int rvNoteId){
        restoreItem(dbNoteId, rvNoteId);
    }

    protected void onInitializeNoteViewAdapter(){}

    protected boolean onItemMove(){
        return false;
    }

    protected void onItemSwipedLeft(@NonNull RecyclerView.ViewHolder viewHolder, long dbPosition, int rvPosition){
        Toast.makeText(getContext(), "Item swiped left ID: " + dbPosition + " (removed)", Toast.LENGTH_SHORT).show();
        removeItem(dbPosition, rvPosition);
    }
    protected void onItemSwipedRight(@NonNull RecyclerView.ViewHolder viewHolder, long dbPosition, int rvPosition){
        Toast.makeText(getContext(), "Item swiped right ID: " + dbPosition, Toast.LENGTH_SHORT).show();
    }

    public NoteViewAdapter.OnClickListener onItemClick(){
        return new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int rvPosition, long dbPosition) {
                Intent intent = new Intent(requireContext(), EditorActivity.class);
                intent.putExtra(RequestCode.REQUEST_CODE, RequestCode.REQUEST_CODE_EDIT_NOTE);
                intent.putExtra(Note.NOTE_ID_EXTRA, dbPosition);
                intent.putExtra(Note.NOTE_POSITION_EXTRA, rvPosition);
                getActivityResultContract().launch(intent);
            }
        };
    }

    protected LiveData<List<Note>> onRetrieveNotes(){
        return mNoteViewModel.getNotesCompat();
    }

    public void removeItem(long dbPosition, int rvPosition){
        //Toast.makeText(getContext(), "Deleting DB ID: " + dbPosition + " with RV Pos: " + rvPosition, Toast.LENGTH_SHORT).show();
        getNoteViewModel().markAsDeleted(dbPosition, true);
        getNoteViewAdapter().notifyItemChanged(rvPosition);
    }

    public void restoreItem(long dbPosition, int rvPosition){
        getNoteViewModel().markAsDeleted(dbPosition, false);
        getNoteViewAdapter().notifyItemChanged(rvPosition);
    }

    public void setFlags(int flags){
        mFlags = flags;
    }
}