package com.phroton.notes.ui.tags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phroton.notes.R;
import com.phroton.notes.Tag;
import com.phroton.notes.TagViewModel;

import java.util.ArrayList;
import java.util.List;

public class TagFragment extends Fragment {
    private TagAdapter mTagAdapter;
    private RecyclerView mTagRecyclerView;
    private TagViewModel mTagViewModel;
    public TagFragment() {
        // Required empty public constructor
    }

    public TagViewModel getViewModel(){
        return mTagViewModel;
    }

    public RecyclerView getRecyclerView(){
        return mTagRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        mTagRecyclerView = recyclerView;
    }

    public void setViewModel(AndroidViewModel viewModel){
        mTagViewModel = (TagViewModel) viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mTagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tag_list, container, false);

        //Initialize RecyclerView
        mTagRecyclerView = (RecyclerView) root.findViewById(R.id.tag_list);
        mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                GridLayoutManager.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false));

        LiveData<List<Tag>> tags = getViewModel().getTags();
        if(tags != null){
            tags.observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
                @Override
                public void onChanged(List<Tag> tags) {
                    if(tags == null){
                        tags = new ArrayList<>();
                    }
                    mTagAdapter = new TagAdapter(getContext(), tags);
                    mTagRecyclerView.setAdapter(mTagAdapter);
                }
            });
        }else{
            List<Tag> errorTag = new ArrayList<>();
            errorTag.add(new Tag("Error 1"));
            errorTag.add(new Tag("Error 2"));
            errorTag.add(new Tag("Error 3"));
            mTagAdapter = new TagAdapter(getContext(), errorTag);
            mTagRecyclerView.setAdapter(mTagAdapter);
        }

        // Inflate the layout for this fragment
        return root;

    }
}