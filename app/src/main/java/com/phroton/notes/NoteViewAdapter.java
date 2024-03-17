package com.phroton.notes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private List<Note> mNotes;
    private Context mContext;

    private OnClickListener mClickListener;

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

        Note currentNote = mNotes.get(holder.getAdapterPosition());
        holder.mTitle.setText(currentNote.getTitle());
        holder.mContent.setText(currentNote.getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnClickListener{
        public void onClick(int position);
    }


}
