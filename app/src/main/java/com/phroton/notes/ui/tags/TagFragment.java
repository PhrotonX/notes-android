package com.phroton.notes.ui.tags;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        //Obtain the data from the DB.
        LiveData<List<Tag>> tags = getViewModel().getTags();
        if(tags != null){
            tags.observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
                @Override
                public void onChanged(List<Tag> tags) {
                    //Set the tags into the TagAdapter.
                    mTagAdapter = new TagAdapter(getContext(), tags);

                    //Set the adapter into the RecyclerView.
                    mTagRecyclerView.setAdapter(mTagAdapter);
                }
            });
        }else{
            //Fill the list with error tags as placeholder value
            //@TODO: Move to TagViewModel. This code is data-related.
            List<Tag> errorTag = new ArrayList<>();
            errorTag.add(new Tag("Error 1"));
            errorTag.add(new Tag("Error 2"));
            errorTag.add(new Tag("Error 3"));

            //Set the tags into the TagAdapter.
            mTagAdapter = new TagAdapter(getContext(), errorTag);

            //Set the adapter into the RecyclerView.
            mTagRecyclerView.setAdapter(mTagAdapter);
        }

        //Set the layout manager.
        if(mTagAdapter.getItemCount() > 0){
            //Set to GridLayoutManager if the item count is greater than 0.
            /*mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
                    GridLayoutManager.DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL, false));*/
            mTagRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }else{
            //Set to LinearLayoutManager if the item count is 0 or less.
            //Used to avoid crashes with empty items on a GridLayoutManager.
            mTagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // Inflate the layout for this fragment
        return root;

    }
}