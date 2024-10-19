package com.phroton.notes.ui.archive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewHolder;
import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;

public class ArchiveFragment extends NoteFragment {
    @Override
    protected NoteViewAdapter.OnBindViewHolderListener onBindViewHolder() {
        return new NoteViewAdapter.OnBindViewHolderListener() {
            @Override
            public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, Note currentData) {
                if(!currentData.isArchived()){
                    holder.hide();
                }
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void archiveItem(long dbPosition, int rvPosition) {
        getNoteViewModel().markAsArchived(dbPosition, false);
        getNoteViewAdapter().notifyItemRemoved(rvPosition);
    }
}
