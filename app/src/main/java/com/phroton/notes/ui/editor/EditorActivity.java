package com.phroton.notes.ui.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.menu_editor_save){
            //TODO: Function for saving here...
        }else if(item.getItemId() == R.id.menu_editor_share){
            //TODO: Function for sharing here...
        }

        return super.onOptionsItemSelected(item);
    }
}