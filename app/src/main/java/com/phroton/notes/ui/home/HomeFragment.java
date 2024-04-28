package com.phroton.notes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.phroton.notes.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NoteViewAdapter mNoteViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        /*NoteViewModel noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);

        LiveData<List<Note>> allNotes = noteViewModel.getNotesCompat();

        if(allNotes != null){
            allNotes.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    if(notes != null){
                        noteViewAdapter.setNotes(notes);
                        noteViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }else{*/
            //Toast.makeText(getContext(), R.string.database_read_error, Toast.LENGTH_SHORT).show();
            List<Note> sampleNote = new ArrayList<>();
            sampleNote.add(new Note("Error 1", "Error Note 1"));
            sampleNote.add(new Note("Error 2", "Error Note 2"));
            sampleNote.add(new Note("Error 3", "Error Note 3"));

            RecyclerView notesView = binding.notesList;
            notesView.setLayoutManager(new LinearLayoutManager(requireContext()));
            mNoteViewAdapter = new NoteViewAdapter(requireContext(), sampleNote);
            notesView.setAdapter(mNoteViewAdapter);
            mNoteViewAdapter.notifyDataSetChanged();
        //}

        /*List<Note> allNotes = noteViewModel.getNotesCompat();
        if(allNotes != null){
            noteViewAdapter.setNotes(allNotes);
            noteViewAdapter.notifyDataSetChanged();
        }*/

        //binding.notesList;

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}