package com.phroton.notes.ui.archive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;

public class ArchiveFragment extends NoteFragment {

    public ArchiveFragment(){

    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container);
    }


}
