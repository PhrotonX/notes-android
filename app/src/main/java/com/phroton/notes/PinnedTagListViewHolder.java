package com.phroton.notes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//@NOTE: Rename into PinnedTagViewHolder
public class PinnedTagListViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView tagName;

    public PinnedTagListViewHolder(@NonNull View itemView){
        super(itemView);
    }
}
