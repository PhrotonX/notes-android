package com.phroton.notes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.RequestCode;
import com.phroton.notes.ui.NoteFragment;
import com.phroton.notes.ui.editor.EditorActivity;

public class HomeFragment extends NoteFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setFlags(NoteViewAdapter.DISPLAY_DEFAULT);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setOnClickListener(NoteViewAdapter.OnClickListener listener) {
        listener = new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int rvPosition, int dbPosition) {
                //Toast.makeText(requireContext(), "Sample Click Message", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), EditorActivity.class);
                intent.putExtra(RequestCode.REQUEST_CODE, RequestCode.REQUEST_CODE_EDIT_NOTE);
                intent.putExtra(Note.NOTE_ID_EXTRA, rvPosition);
                mActivityResultContract.launch(intent);
            }
        };

        super.setOnClickListener(listener);
    }

    @Override
    public void onActivityResult(ActivityResultLauncher<Intent> activityResult) {
        activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Note note;
                switch(o.getResultCode()){
                    case EditorActivity.RESULT_OK:
                        note = Note.unpackCurrentNote(o.getData(), true);
                        Toast.makeText(getContext(), "MainActivity noteId: " + note.getId(), Toast.LENGTH_SHORT).show();
                        if(note.getId() == -1){
                            Toast.makeText(getContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                        }

                        mNoteViewModel.update(note);
                        break;
                    case EditorActivity.RESULT_DELETE:
                        int noteId = o.getData().getIntExtra(Note.NOTE_ID_EXTRA, -1);
                        mNoteViewModel.markAsDeleted(noteId, true);
                        break;
                    case EditorActivity.RESULT_CANCELED:
                        Toast.makeText(getContext(), "EditorActivity: Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "EditorActivity: Error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        super.onActivityResult(activityResult);
    }
}