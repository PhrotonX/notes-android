package com.phroton.notes.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;

import java.util.List;

public class SearchFragment extends NoteFragment {
    String mQuery;
    public SearchFragment(String query){
        mQuery = query;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onInitializeNoteViewAdapter() {
        getNoteViewAdapter().setQuery(mQuery);
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    protected LiveData<List<Note>> onRetrieveNotes() {
        return getNoteViewModel().search(mQuery, -1);
    }

    @Override
    public void archiveItem(long dbPosition, int rvPosition) {
        return;
    }

    @Override
    public void removeItem(long dbPosition, int rvPosition) {
        return;
    }
}
