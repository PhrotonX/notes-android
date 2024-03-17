package com.phroton.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    public TextView mTitle;
    public TextView mContent;

    public View mView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitle = (TextView)itemView.findViewById(R.id.noteTitle);
        mContent = (TextView)itemView.findViewById(R.id.noteContent);
        mView = itemView;
    }

}
