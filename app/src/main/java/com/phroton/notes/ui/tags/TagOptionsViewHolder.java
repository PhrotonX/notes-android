package com.phroton.notes.ui.tags;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.Tag;
import com.phroton.notes.data.taggednote.TaggedNote;

public class TagOptionsViewHolder extends RecyclerView.ViewHolder {
    public View root;
    public CheckBox option;
    public TagOptionsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Tag tag){

    }
}
