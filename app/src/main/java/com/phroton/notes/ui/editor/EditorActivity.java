package com.phroton.notes.ui.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;

import com.phroton.notes.R;

public class EditorActivity extends AppCompatActivity {
    private EditorViewModel mEditorViewModel;

    private EditText mEditorTitle;
    private EditText mEditorContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mEditorViewModel = new ViewModelProvider(this).get(EditorViewModel.class);
    }
}