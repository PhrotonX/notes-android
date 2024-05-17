package com.phroton.notes.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.NoteViewAdapter;
import com.phroton.notes.databinding.FragmentGalleryBinding;
import com.phroton.notes.ui.editor.EditorActivity;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private NoteViewAdapter mNoteViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);



        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView notesView = binding.galleryList;
        notesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mNoteViewAdapter = new NoteViewAdapter(requireContext());
        mNoteViewAdapter.setOnClickListener(new NoteViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(requireContext(), "Sample Click Message", Toast.LENGTH_SHORT).show();
            }
        });
        notesView.setAdapter(mNoteViewAdapter);
        mNoteViewAdapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}