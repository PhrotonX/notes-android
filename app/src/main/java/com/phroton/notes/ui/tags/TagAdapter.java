package com.phroton.notes.ui.tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.R;
import com.phroton.notes.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
    private Context mContext;
    private List<Tag> mTag;

    public TagAdapter(Context context){
        mContext = context;

        //Fill the list with error tags as placeholder value
        /*//@TODO: Move to TagViewModel. This code is data-related.
        List<Tag> errorTag = new ArrayList<>();
        errorTag.add(new Tag("Error 1"));
        errorTag.add(new Tag("Error 2"));
        errorTag.add(new Tag("Error 3"));*/

        mTag = new ArrayList<>();
    }
    public TagAdapter(Context context, List<Tag> tag){
        mContext = context;

        //Set the value of mTag, but use empty list if the tag list is null.
        mTag = (tag != null) ? tag : new ArrayList<>();
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.bind(mTag.get(position));
    }

    @Override
    public int getItemCount() {
        return (mTag != null) ? mTag.size() : 0;
    }
}
