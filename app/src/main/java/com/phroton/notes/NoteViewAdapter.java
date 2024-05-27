package com.phroton.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private List<Note> mNotes;
    private List<Integer> mNoteId = new ArrayList<Integer>();
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
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(mNotes != null) {
            Note currentNote = mNotes.get(position);

            mNoteId.add(currentNote.getId());

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
                        if(mClickListener != null){
                            mClickListener.onClick(position);
                        }
                    }
                });
            //}
        }
    }

    public int getNoteId(int position){
        return mNoteId.get(position);
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    public void setOnClickListener(OnClickListener clickListener){
        this.mClickListener = clickListener;
    }


    public interface OnClickListener {
        void onClick(int position);
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
    }
}
