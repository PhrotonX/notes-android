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
import com.phroton.notes.R;
import com.phroton.notes.Tag;
import com.phroton.notes.data.taggednote.TaggedNote;
import com.phroton.notes.data.taggednote.TaggedNoteViewModel;
import com.phroton.notes.ui.NoteFragment;

import java.util.List;

public class TaggedNoteFragment extends NoteFragment {

    private Tag mTag;

    private TaggedNoteViewModel mTaggedNoteViewModel;

    public TaggedNoteFragment(){

    }

    public TaggedNoteViewModel getTaggedNoteViewModel(){ return mTaggedNoteViewModel; }

    @Override
    protected NoteViewAdapter.OnBindViewHolderListener onBindViewHolder() {
        return new NoteViewAdapter.OnBindViewHolderListener() {
            @Override
            public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, Note currentData) {
                //@TODO: Temporary only. Must utilize TaggedNote table in the schema for faster performance.
                //@TODO: Make tag ID long instead of int.
                if((currentData.isDeleted() || currentData.isArchived()) && currentData.tag != (long)mTag.id){
                    holder.hide();
                }
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //Obtain the tag from bundle.
        if(getArguments() != null){
            mTag = getArguments().getParcelable(Tag.EXTRA_TAG);
        }

        Toast.makeText(getContext(), "Current Tag: " + mTag.mName, Toast.LENGTH_SHORT).show();

        //Initialize the view model.
        mTaggedNoteViewModel = new ViewModelProvider(this).get(TaggedNoteViewModel.class);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*
        getParentFragmentManager().beginTransaction()
                .remove(this)
                .commit();*/
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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