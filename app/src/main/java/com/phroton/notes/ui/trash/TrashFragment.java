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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(ActivityResultLauncher<Intent> activityResult) {
        super.onActivityResult(activityResult);
    }

    @Override
    public void setFlags(int flags) {
        super.setFlags(flags);
    }

    @Override
    public void setOnClickListener(NoteViewAdapter.OnClickListener listener) {
        super.setOnClickListener(listener);
    }
}