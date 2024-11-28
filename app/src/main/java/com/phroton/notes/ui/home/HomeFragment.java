package com.phroton.notes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewHolder;
import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;

public class HomeFragment extends NoteFragment {
    @Override
    protected NoteViewAdapter.OnBindViewHolderListener onBindViewHolder() {
        return new NoteViewAdapter.OnBindViewHolderListener() {
            @Override
            public void onBindViewHolder(@NonNull NoteViewHolder holder, int position, Note currentData) {
                if(currentData.isDeleted() || currentData.isArchived()){
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void onActivityResultDelete(ActivityResult result, Note note, long dbNoteId, int rvNoteId) {
        return;
    }
}