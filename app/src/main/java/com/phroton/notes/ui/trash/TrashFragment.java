package com.phroton.notes.ui.trash;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.R;
import com.phroton.notes.ui.NoteFragment;


public class TrashFragment extends NoteFragment {
    @Override
    public View provideFragment(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public ActivityResultLauncher<Intent> onActivityResult() {
        return null;
    }

    @Override
    public void setFlags(int flags) {
        super.setFlags(flags);
    }

    @Override
    public NoteViewAdapter.OnClickListener onItemClick() {
        return null;
    }
}