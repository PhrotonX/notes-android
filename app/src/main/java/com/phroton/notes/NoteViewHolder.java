package com.phroton.notes;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
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

    public void bind(Note note, int position, String query){
        String shortenedText;
        String dbInfo = "DB: " + note.getId() + " ";
        String rvInfo = "DB: " + position + " ";

        if(note.getTitle().length() >= 100) {
            shortenedText = note.getTitle().substring(0, 100) + "...";
            mTitle.setText(highlightQueriedText(dbInfo + shortenedText, query));
        }else{
            mTitle.setText(highlightQueriedText(dbInfo + note.getTitle(), query));
        }

        if(note.getContent().length() >= 200){
            shortenedText = note.getContent().substring(0, 200) + "...";
            mContent.setText(highlightQueriedText(rvInfo + shortenedText, query));
        }else{
            mContent.setText(highlightQueriedText(rvInfo + note.getContent(), query));
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

    private SpannableString highlightQueriedText(String text, String query){
        if(query != null){
            SpannableString highlightedText;
            highlightedText = new SpannableString(text);
            int begin = text.indexOf(query);
            int end = begin + (query.length());
            if(begin != -1){
                highlightedText.setSpan(new BackgroundColorSpan(Color.YELLOW), begin, end, 0);
            }

            return highlightedText;
        }else{
            return new SpannableString(text);
        }

    }
}
