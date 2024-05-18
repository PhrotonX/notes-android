package com.phroton.notes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder>{
    private List<Note> mNotes;
    private Context mContext;

    private OnClickListener mClickListener;

    public NoteViewAdapter(Context context){
        this.mContext = context;
        this.mNotes = null;
    }

    public NoteViewAdapter(Context context, List<Note> notes){
        this.mContext = context;
        this.mNotes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(mNotes != null) {
            Note currentNote = mNotes.get(position);

            String shortenedText;

            if(currentNote.getTitle().length() >= 100) {
                shortenedText = currentNote.getTitle().substring(0, 100) + "...";
                holder.mTitle.setText(shortenedText);
            }else{
                holder.mTitle.setText(currentNote.getTitle());
            }

            if(currentNote.getContent().length() >= 200){
                shortenedText = currentNote.getContent().substring(0, 200) + "...";
                holder.mContent.setText(shortenedText);
            }else{
                holder.mContent.setText(currentNote.getContent());
            }

            //if(mClickListener != null){
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.onClick(holder.getAdapterPosition());
                    }
                });
            //}
        }
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
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


    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    public interface OnClickListener{
        public void onClick(int position);
    }

    public void setOnClickListener(OnClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
    }
}
