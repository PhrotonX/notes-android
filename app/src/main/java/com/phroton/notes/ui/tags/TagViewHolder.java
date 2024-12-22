package com.phroton.notes.ui.tags;

import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.R;
import com.phroton.notes.Tag;

public class TagViewHolder extends RecyclerView.ViewHolder {
    public TextView tagName;
    public View view;

    /**
     * Initializes values for all view instances.
     * @param itemView The itemView instance where data shall be bound.
     * */
    public TagViewHolder(@NonNull View itemView) {
        super(itemView);

        tagName = (TextView)itemView.findViewById(R.id.tag_name);

        view = itemView;
    }

    /**
     * Binds the data into the item view.
     *
     * @param tag The data that shall be bound into the itemView.
     * */
    public void bind(Tag tag){
        tagName.setText(tag.mName);
    }


}
