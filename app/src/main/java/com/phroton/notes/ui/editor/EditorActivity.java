package com.phroton.notes.ui.editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
    private Note mNote;
    private NoteViewModel mNoteViewModel = null;
    /*
    * \details This position number is from RecyclerView or RV, which is zero-based or index 0. Lists are
    * also zero-based.
    * However, databases or DB are 1-based or index 1. Increment 1 value for accessing DB items.
    * */
    private int mRvPosition = -1;
    private int mDbPosition = 0;
    private RequestCode mRequestCode;
    private View mView;

    public static final int RESULT_DELETE = 50000;
    public static final int RESULT_REMOVE = 50001;
    public static final int RESULT_RESTORE = 50002;

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
                    @SuppressLint("NonConstantResourceId")
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
                case REQUEST_CODE_EDIT_NOTE:
                    mDbPosition = intent.getIntExtra(Note.NOTE_ID_EXTRA, -1);
                    mRvPosition = intent.getIntExtra(Note.NOTE_POSITION_EXTRA, -1);
                    Toast.makeText(this, "EditorAcitvity DbPosition: " + mDbPosition, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "EditorAcitvity RvPosition: " + mRvPosition, Toast.LENGTH_SHORT).show();

                    if(mDbPosition != -1){
                        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
                        mNoteViewModel.getNotesCompat().observe(this, new Observer<List<Note>>() {
                            @Override
                            public void onChanged(List<Note> notes) {
                                //RV is Index 0.
                                int size = notes.size();

                                //Check size to avoid IndexOutOfBoundsException.
                                if(mRvPosition < size){
                                    mNote = notes.get(mRvPosition);
                                    mEditorTitle.setText(mNote.getTitle());
                                    mEditorContent.setText(mNote.getContent());
                                    ChangeBackgroundColor(mNote.getColor());
                                }

                            }
                        });
                    }else{
                        Toast.makeText(this, "dbPosition: " + mDbPosition, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "rvPosition: " + mRvPosition, Toast.LENGTH_SHORT).show();
                    }

                    break;
                case REQUEST_CODE_UNKNOWN:
                default:
                    break;
            }
        }else{
            Toast.makeText(this, "requestCode is null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(R.id.menu_editor_delete);
        MenuItem restore = menu.findItem(R.id.menu_editor_restore);
        MenuItem moveToTrash = menu.findItem(R.id.menu_editor_remove);

        switch(mRequestCode){
            case REQUEST_CODE_EDIT_NOTE:
                if(mNote != null){
                    if(mNote.getIsDeleted()){
                        moveToTrash.setVisible(false);
                    }
                }
            case REQUEST_CODE_CREATE_NOTE:
                delete.setVisible(false);
                restore.setVisible(false);

                if(mRequestCode != RequestCode.REQUEST_CODE_EDIT_NOTE) moveToTrash.setVisible(false);
                break;
            default:
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_editor_delete:
                setResult(RESULT_DELETE, Note.packCurrentNote(packCurrentNote(), true));
                finish();
                break;
            case R.id.menu_editor_save:
                boolean isEditing = mRequestCode == RequestCode.REQUEST_CODE_EDIT_NOTE;
                setResult(RESULT_OK, Note.packCurrentNote(packCurrentNote(), isEditing));
                finish();
                break;
            case R.id.menu_editor_share:
                //TODO: Function for sharing here...
                break;
            case R.id.menu_editor_remove:
                setResult(RESULT_REMOVE, packCurrentNotePosition());
                finish();
                break;
            case R.id.menu_editor_restore:
                setResult(RESULT_RESTORE, packCurrentNotePosition());
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
            //DB is Index 1
            note.setId(mDbPosition);
        }

        note.setColor(mColor);

        return note;
    }

    private Intent packCurrentNotePosition(){
        Intent intent = new Intent();
        intent.putExtra(Note.NOTE_ID_EXTRA, mDbPosition);
        intent.putExtra(Note.NOTE_POSITION_EXTRA, mRvPosition);
        return intent;
    }
}