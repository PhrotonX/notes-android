package com.phroton.notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
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
import com.phroton.notes.databinding.FragmentNotesBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class NoteFragment extends Fragment {
    protected ActivityResultLauncher<Intent> mActivityResultContract;
    protected FragmentNotesBinding binding;
    protected Context mContext;
    protected NoteViewAdapter mNoteViewAdapter;
    //protected ActivityResultLauncher<Intent> mEditContent;
    protected int mFlags;
    protected LifecycleOwner mLifecycleOwner;
    private NoteViewAdapter.OnClickListener mListener;
    protected NoteViewModel mNoteViewModel;
            //= new ViewModelProvider(this).get(NoteViewModel.class);


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mContext = getContext();
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mLifecycleOwner = getViewLifecycleOwner();

        RecyclerView notesView = binding.notesList;
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
        binding = null;
    }

    public abstract ActivityResultLauncher<Intent> onActivityResult();

    public void setFlags(int flags){
        mFlags = flags;
    }

    public abstract NoteViewAdapter.OnClickListener onItemClick();
}