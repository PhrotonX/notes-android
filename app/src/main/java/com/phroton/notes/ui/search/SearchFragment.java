package com.phroton.notes.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;

public class SearchFragment extends NoteFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setFlags(NoteViewAdapter.DISPLAY_SEARCH);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    
}
