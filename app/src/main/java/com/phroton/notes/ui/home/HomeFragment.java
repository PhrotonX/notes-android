package com.phroton.notes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Note;
import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.NoteViewModel;
import com.phroton.notes.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NoteViewAdapter noteViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/
         /*NoteViewModel noteViewModel =
                new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.insert(new Note("Sample Data 1", "Sample Content 1 The quick brown fox jumps over the lazy dog."));
        noteViewModel.insert(new Note("Sample Data 2", "Sample Content 2 The quick brown fox jumps over the lazy dog."));
        noteViewModel.insert(new Note("Sample Data 3", "Sample Content 3 The quick brown fox jumps over the lazy dog."));*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView notesView = binding.notesList;
        notesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteViewAdapter = new NoteViewAdapter(requireContext());
        notesView.setAdapter(noteViewAdapter);

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