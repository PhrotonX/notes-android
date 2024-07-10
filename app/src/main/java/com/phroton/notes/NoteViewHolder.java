package com.phroton.notes;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    public TextView mTitle;
    public TextView mContent;

    public View mView;
    public CardView mCardView;

    private Context mContext;

    public NoteViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        mTitle = (TextView)itemView.findViewById(R.id.noteTitle);
        mContent = (TextView)itemView.findViewById(R.id.noteContent);
        mView = itemView;
        mCardView = (CardView)itemView.findViewById(R.id.noteCard);
        mContext = context;
    }

    public void bind(Note note, int position){
        String shortenedText;

        if(note.getTitle().length() >= 100) {
            shortenedText = note.getTitle().substring(0, 100) + "...";
            mTitle.setText("DB: " + note.getId() + " - " + shortenedText);
        }else{
            mTitle.setText("DB: " + note.getId() + " - " + note.getTitle());
        }

        if(note.getContent().length() >= 200){
            shortenedText = note.getContent().substring(0, 200) + "...";
            mContent.setText("RV: " + position + " - " + shortenedText);
        }else{
            mContent.setText("RV: " + position + " - " + note.getContent());
        }

        if(note.getColor() == 0x0){
            mCardView.setCardBackgroundColor(mContext.getColor(R.color.background_white));
        }else{
            mCardView.setCardBackgroundColor(mContext.getColor(note.getColor()));
        }
    }

    public void hide(){
        mCardView.setVisibility(View.GONE);
    }
}
