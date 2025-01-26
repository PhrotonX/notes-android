package com.phroton.notes.ui.taggednote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewHolder;
import com.phroton.notes.Tag;
import com.phroton.notes.data.taggednote.TaggedNote;
import com.phroton.notes.data.taggednote.TaggedNoteViewModel;
import com.phroton.notes.ui.NoteFragment;

import java.util.List;

public class TaggedNoteFragment extends NoteFragment {

    private Tag mTag;

    private TaggedNoteViewModel mTaggedNoteViewModel;

    public TaggedNoteFragment(Tag tag){
        //Initialize the view model.
        mTaggedNoteViewModel = new ViewModelProvider(this).get(TaggedNoteViewModel.class);

        //Set the current set tag.
        mTag = tag;
    }

    public TaggedNoteViewModel getTaggedNoteViewModel(){ return mTaggedNoteViewModel; }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Current Tag: " + mTag.mName, Toast.LENGTH_SHORT).show();
        return null;
    }

    /*
    @Override
    public void onInitializeRecycleViewAdapter() {
        mTaggedNoteViewModel.getTaggedNotesById(mTag.id).observe(getViewLifecycleOwner(), new Observer<List<TaggedNote>>() {
            @Override
            public void onChanged(List<TaggedNote> taggedNotes) {

            }
        });
    }*/
}
