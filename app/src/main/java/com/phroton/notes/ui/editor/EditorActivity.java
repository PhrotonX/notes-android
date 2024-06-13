package com.phroton.notes.ui.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewModel;
import com.phroton.notes.R;
import com.phroton.notes.RequestCode;

import java.util.List;

public class EditorActivity extends AppCompatActivity {
    //private EditorViewModel mEditorViewModel;

    private EditText mEditorTitle;
    private EditText mEditorContent;

    private NoteViewModel mNoteViewModel = null;

    public static final String EDITOR_TITLE_EXTRA = "EDITOR_TITLE_EXTRA";
    public static final String EDITOR_CONTENT_EXTRA = "EDITOR_CONTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mEditorTitle = (EditText) findViewById(R.id.editorTitle);
        mEditorContent = (EditText) findViewById(R.id.editorContent);
        //mEditorViewModel = new ViewModelProvider(this).get(EditorViewModel.class);

        Intent intent = getIntent();
        RequestCode requestCode;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestCode = intent.getSerializableExtra(RequestCode.REQUEST_CODE, RequestCode.class);
        }else{
            requestCode = (RequestCode) intent.getSerializableExtra(RequestCode.REQUEST_CODE);
        }

        if(requestCode != null){
            switch(requestCode){
                case REQUEST_CODE_CREATE_NOTE:
                    break;
                case REQUEST_CODE_EDIT_NOTE:
                    int position = intent.getIntExtra(Note.NOTE_ID_EXTRA, -1);

                    if(position != -1){
                        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
                        mNoteViewModel.getNotesCompat().observe(this, new Observer<List<Note>>() {
                            @Override
                            public void onChanged(List<Note> notes) {
                                Note note = notes.get(position);
                                mEditorTitle.setText(note.getTitle());
                                mEditorContent.setText(note.getContent());
                            }
                        });
                    }else{
                        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
                    }

                    break;
                case REQUEST_CODE_UNKNOWN:
                    break;
                default:
                    break;
            }
        }else{
            Toast.makeText(this, "requestCode is null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        getMenuInflater().inflate(R.menu.menu_color, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.menu_editor_save){
            //TODO: Function for saving here...

            Intent intent = new Intent();
            /*intent.putExtra(EDITOR_TITLE_EXTRA, mEditorViewModel.getTitle());
            intent.putExtra(EDITOR_CONTENT_EXTRA, mEditorViewModel.getContent());*/

            intent.putExtra(EDITOR_TITLE_EXTRA, mEditorTitle.getText().toString());
            intent.putExtra(EDITOR_CONTENT_EXTRA, mEditorContent.getText().toString());

            setResult(RESULT_OK, intent);
            finish();
        }else if(item.getItemId() == R.id.menu_editor_share){
            //TODO: Function for sharing here...
        }

        return super.onOptionsItemSelected(item);
    }
}