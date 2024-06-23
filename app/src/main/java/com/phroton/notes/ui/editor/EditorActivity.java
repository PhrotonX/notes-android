package com.phroton.notes.ui.editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewModel;
import com.phroton.notes.R;
import com.phroton.notes.RequestCode;

import java.util.List;

public class EditorActivity extends AppCompatActivity {
    //private EditorViewModel mEditorViewModel;
    private int mColor;

    private EditText mEditorTitle;
    private EditText mEditorContent;

    private NoteViewModel mNoteViewModel = null;
    /**
     * \remarks Position value + 1 is always the same as the Note ID from the DB once RequestCode
     * is equal to REQUEST_CODE_EDIT_NOTE. */
    private int mPosition = 0;
    private RequestCode mRequestCode;
    private View mView;

    public static final int RESULT_DELETE = 50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mView = findViewById(R.id.editActivity);
        mEditorTitle = (EditText) findViewById(R.id.editorTitle);
        mEditorContent = (EditText) findViewById(R.id.editorContent);
        //mEditorViewModel = new ViewModelProvider(this).get(EditorViewModel.class);

        Intent intent = getIntent();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            mRequestCode = intent.getSerializableExtra(RequestCode.REQUEST_CODE, RequestCode.class);
        }else{
            mRequestCode = (RequestCode) intent.getSerializableExtra(RequestCode.REQUEST_CODE);
        }

        getSupportFragmentManager().setFragmentResultListener(ColorDialogFragment.REQUEST_COLOR_UPDATED,
                this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        int dialogResult = result.getInt(ColorDialogFragment.EXTRA_COLOR_ID);
                        switch(dialogResult){
                            case R.id.radio_color_yellow:
                                ChangeBackgroundColor(R.color.background_yellow);
                                break;
                            case R.id.radio_color_blue:
                                ChangeBackgroundColor(R.color.background_blue);
                                break;
                            case R.id.radio_color_green:
                                ChangeBackgroundColor(R.color.background_green);
                                break;
                            case R.id.radio_color_red:
                                ChangeBackgroundColor(R.color.background_red);
                                break;
                            case R.id.radio_color_orange:
                                ChangeBackgroundColor(R.color.background_orange);
                                break;
                            case R.id.radio_color_purple:
                                ChangeBackgroundColor(R.color.background_purple);
                                break;
                            case R.id.radio_color_pink:
                                ChangeBackgroundColor(R.color.background_pink);
                                break;
                            case R.id.radio_color_gray:
                                ChangeBackgroundColor(R.color.background_gray);
                                break;
                            case R.id.radio_color_white:
                            default:
                                ChangeBackgroundColor(R.color.background_white);
                                break;
                        }

                    }
                });

        if(mRequestCode != null){
            switch(mRequestCode){
                case REQUEST_CODE_CREATE_NOTE:
                    MenuItem deleteItem = (MenuItem)findViewById(R.id.menu_editor_delete);
                    deleteItem.setEnabled(false);
                    break;
                case REQUEST_CODE_EDIT_NOTE:
                    mPosition = intent.getIntExtra(Note.NOTE_ID_EXTRA, -1);

                    if(mPosition != -1){
                        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
                        mNoteViewModel.getNotesCompat().observe(this, new Observer<List<Note>>() {
                            @Override
                            public void onChanged(List<Note> notes) {
                                Note note = notes.get(mPosition);
                                mEditorTitle.setText(note.getTitle());
                                mEditorContent.setText(note.getContent());
                                ChangeBackgroundColor(note.getColor());
                            }
                        });
                    }else{
                        Toast.makeText(this, "position: " + mPosition, Toast.LENGTH_SHORT).show();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_editor_save:
                //TODO: Function for saving here...

                boolean isEditing = (mRequestCode == RequestCode.REQUEST_CODE_EDIT_NOTE) ? true : false;
                setResult(RESULT_OK, Note.packCurrentNote(packCurrentNote(), isEditing));
                finish();
                break;
            case R.id.menu_editor_share:
                //TODO: Function for sharing here...
                break;
            case R.id.menu_editor_delete:
                Intent intent = new Intent();
                intent.putExtra(Note.NOTE_ID_EXTRA, getNoteId());
                setResult(RESULT_DELETE, intent);
                finish();
                break;
            case R.id.menu_editor_color:
                ColorDialogFragment dialog = new ColorDialogFragment();
                dialog.show(getSupportFragmentManager(), "ColorDialogFragment");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ChangeBackgroundColor(int colorRes){
        mView.setBackgroundResource(colorRes);
        mColor = colorRes;
    }

    private Note packCurrentNote(){
        Note note = new Note(mEditorTitle.getText().toString(), mEditorContent.getText().toString());

        if(mRequestCode == RequestCode.REQUEST_CODE_EDIT_NOTE){
            note.setId(getNoteId());
        }

        note.setColor(mColor);

        return note;
    }

    private int getNoteId(){
        return mPosition + 1;
    }
}