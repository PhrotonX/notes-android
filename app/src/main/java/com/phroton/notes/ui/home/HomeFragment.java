package com.phroton.notes.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.phroton.notes.databinding.FragmentHomeBinding;
import com.phroton.notes.ui.editor.EditorActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NoteViewAdapter mNoteViewAdapter;

    private ActivityResultLauncher<Intent> mGetContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView notesView = binding.notesList;
        notesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mNoteViewAdapter = new NoteViewAdapter(requireContext());
        mNoteViewAdapter.setOnClickListener(new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                //Toast.makeText(requireContext(), "Sample Click Message", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), EditorActivity.class);
                intent.putExtra(RequestCode.REQUEST_CODE, RequestCode.REQUEST_CODE_EDIT_NOTE);
                intent.putExtra(Note.NOTE_ID_EXTRA, position);
                mGetContent.launch(intent);
            }
        });


        NoteViewModel noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);

        LiveData<List<Note>> allNotes = noteViewModel.getNotesCompat();

        if(allNotes != null){
            allNotes.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    if(notes != null){
                        mNoteViewAdapter.setNotes(notes);
                        mNoteViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), R.string.database_read_error, Toast.LENGTH_SHORT).show();
            List<Note> sampleNote = new ArrayList<>();
            sampleNote.add(new Note("Error 1", "Error Note 1"));
            sampleNote.add(new Note("Error 2", "Error Note 2"));
            sampleNote.add(new Note("Error 3", "Error Note 3"));

            mNoteViewAdapter.setNotes(sampleNote);
            mNoteViewAdapter.notifyDataSetChanged();
        }

        //@NOTE: Handle request after editing a note.
        mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                switch(o.getResultCode()){
                    case EditorActivity.RESULT_OK:
                        //Toast.makeText(getContext(), "Sample activity result", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "RESULT_OK",
                                Toast.LENGTH_SHORT).show();
                        Intent data = o.getData();
                        Note note = new Note(data.getStringExtra(EditorActivity.EDITOR_TITLE_EXTRA),
                                data.getStringExtra(EditorActivity.EDITOR_CONTENT_EXTRA));
                        note.setColor(data.getIntExtra(EditorActivity.EDITOR_COLOR_EXTRA,
                                R.color.background_white));
                        note.setId(data.getIntExtra(EditorActivity.EDITOR_ID_EXTRA, -1));

                        if(note.getColor() == 0x0) {
                            note.setColor(R.color.background_white);
                        }
                        if(note.getId() == -1){
                            Toast.makeText(getContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                        }

                        noteViewModel.update(note);
                        break;
                    default:
                        break;
                }
            }
        });

        /*List<Note> allNotes = noteViewModel.getNotesCompat();
        if(allNotes != null){
            noteViewAdapter.setNotes(allNotes);
            noteViewAdapter.notifyDataSetChanged();
        }*/

        //binding.notesList;

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        notesView.setAdapter(mNoteViewAdapter);
        //mNoteViewAdapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}