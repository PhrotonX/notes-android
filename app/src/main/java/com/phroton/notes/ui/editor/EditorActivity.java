package com.phroton.notes.ui.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.phroton.notes.R;

public class EditorActivity extends AppCompatActivity {
    //private EditorViewModel mEditorViewModel;

    private EditText mEditorTitle;
    private EditText mEditorContent;

    public static final String EDITOR_TITLE_EXTRA = "EDITOR_TITLE_EXTRA";
    public static final String EDITOR_CONTENT_EXTRA = "EDITOR_CONTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mEditorTitle = (EditText) findViewById(R.id.editorTitle);
        mEditorContent = (EditText) findViewById(R.id.editorContent);
        //mEditorViewModel = new ViewModelProvider(this).get(EditorViewModel.class);
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

            Intent intent = new Intent();
            /*intent.putExtra(EDITOR_TITLE_EXTRA, mEditorViewModel.getTitle());
            intent.putExtra(EDITOR_CONTENT_EXTRA, mEditorViewModel.getContent());*/

            intent.putExtra(EDITOR_TITLE_EXTRA, mEditorTitle.getText());
            intent.putExtra(EDITOR_CONTENT_EXTRA, mEditorContent.getText());

            setResult(RESULT_OK, intent);
            finish();
        }else if(item.getItemId() == R.id.menu_editor_share){
            //TODO: Function for sharing here...
        }

        return super.onOptionsItemSelected(item);
    }
}