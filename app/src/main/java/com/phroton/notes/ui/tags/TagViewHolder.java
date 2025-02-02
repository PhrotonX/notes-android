package com.phroton.notes.ui.tags;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.R;
import com.phroton.notes.Tag;

public class TagViewHolder extends RecyclerView.ViewHolder {
    public TextView tagName;
    public View view;
    private int mSelectedTagId = -1;

    /**
     * Initializes values for all view instances.
     * @param itemView The itemView instance where data shall be bound.
     * */
    public TagViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tagName = (TextView)itemView.findViewById(R.id.tag_name);
        this.view = itemView;
    }

    /**
     * Binds the data into the item view.
     *
     * @param tag The data that shall be bound into the itemView.
     * */
    public void bind(Tag tag){
        tagName.setText(tag.mName);
    }

    public int getSelectedTagId(){
        return mSelectedTagId;
    }

    public void setSelectedTagId(int position){
        mSelectedTagId = position;
    }
}
