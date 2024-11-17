package com.phroton.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TagListAdapter extends RecyclerView.Adapter<TagListViewHolder> {
    private List<Tag> mTags;
    private OnClickListener mClickListener;

    public TagListAdapter(List<Tag> tags){
        mTags = tags;
    }
    @NonNull
    @Override
    public TagListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.nav_tag_item, parent, false);

        return new TagListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagListViewHolder holder, int position) {
        holder.tagName.setText(mTags.get(position).mName);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnClickListener(OnClickListener listener){
        mClickListener = listener;
    }

    public interface OnClickListener{
        void onClick(int position);
    }
}
