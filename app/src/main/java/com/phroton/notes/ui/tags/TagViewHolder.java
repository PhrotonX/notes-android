package com.phroton.notes.ui.tags;

import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Tag;

public class TagViewHolder extends RecyclerView.ViewHolder {
    public TextView tagName;
    public View view;

    public TagViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Tag tag){
        tagName.setText(tag.mName);
    }


}
