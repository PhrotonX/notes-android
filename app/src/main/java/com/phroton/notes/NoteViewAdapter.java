package com.phroton.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private List<Note> mNotes;
    private Context mContext;
    private String mQuery = null;
    private OnClickListener mClickListener;
    private OnBindViewHolderListener mBindViewHolderListener;

    public NoteViewAdapter(Context context){
        this.mContext = context;
        this.mNotes = new ArrayList<>();
        Init();
    }

    public NoteViewAdapter(Context context, List<Note> notes){
        this.mContext = context;
        this.mNotes = notes;
        Init();
    }

    public NoteViewAdapter(Context context, List<Note> notes, OnClickListener listener){
        this.mContext = context;
        this.mNotes = notes;
        this.mClickListener = listener;
        Init();
    }

    public void Init(){

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note, parent, false);

        return new NoteViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(mNotes != null) {

            Note currentData = mNotes.get(position);

            if(currentData != null){

                if(mBindViewHolderListener != null)
                    mBindViewHolderListener.onBindViewHolder(holder, position, currentData);

                holder.bind(currentData, position, mQuery);
                holder.itemView.setTag(currentData.getId());

                //if(mClickListener != null){
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mClickListener != null){
                            mClickListener.onClick(position, currentData.getId());
                        }
                    }
                });
            }


        }else{
            holder.hide();
        }

    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    public void setOnBindViewHolderListener(OnBindViewHolderListener listener){
        this.mBindViewHolderListener = listener;
    }
    public void setOnClickListener(OnClickListener clickListener){
        this.mClickListener = clickListener;
    }

    public void setQuery(String query){
        mQuery = query;
    }

    public interface OnBindViewHolderListener {
        void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position, Note currentData);
    }

    public interface OnClickListener {
        void onClick(int rvPosition, long dbPosition);
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
    }
}
