package com.phroton.notes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewModel;
import com.phroton.notes.R;
import com.phroton.notes.RequestCode;
//import com.phroton.notes.databinding.FragmentNotesBinding;
import com.phroton.notes.ui.NoteFragment;
import com.phroton.notes.ui.editor.EditorActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends NoteFragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setFlags(NoteViewAdapter.DISPLAY_DEFAULT);

        MenuHost menuHost = getActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onInitializeView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public ActivityResultLauncher<Intent> onActivityResult() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
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

                        getNoteViewModel().update(note);
                        break;
                    case EditorActivity.RESULT_REMOVE:
                        int noteId = o.getData().getIntExtra(Note.NOTE_ID_EXTRA, -1);
                        getNoteViewModel().markAsDeleted(noteId, true);
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
    }

    @Override
    public NoteViewAdapter.OnClickListener onItemClick() {
        return new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int rvPosition, int dbPosition) {
                Intent intent = new Intent(requireContext(), EditorActivity.class);
                intent.putExtra(RequestCode.REQUEST_CODE, RequestCode.REQUEST_CODE_EDIT_NOTE);
                intent.putExtra(Note.NOTE_ID_EXTRA, rvPosition);
                getActivityResultContract().launch(intent);
            }
        };
    }


}