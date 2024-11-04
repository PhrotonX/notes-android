package com.phroton.notes.ui.taggednote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewHolder;
import com.phroton.notes.ui.NoteFragment;

public class TaggedNoteFragment extends NoteFragment {

    @Override
    protected NoteViewAdapter.OnBindViewHolderListener onBindViewHolder() {
        return new NoteViewAdapter.OnBindViewHolderListener() {
            @Override
            public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, Note currentData) {

            }
        };
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
}
