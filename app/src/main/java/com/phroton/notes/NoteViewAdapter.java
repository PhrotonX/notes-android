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
    private Context mContext;

    public static final int DISPLAY_DEFAULT = 1;
    public static final int DISPLAY_DELETED = 2;
    public static final int DISPLAY_ARCHIVED = 4;
    public static final int DISPLAY_TAGGED = 8;
    public static final int DISPLAY_SEARCH = 16;

    private int mFlags = 0;

    private OnClickListener mClickListener;

    public NoteViewAdapter(Context context, int flags){
        this.mContext = context;
        this.mNotes = new ArrayList<>();
        this.mFlags = flags;
        Init();
    }

    public NoteViewAdapter(Context context, List<Note> notes, int flags){
        this.mContext = context;
        this.mNotes = notes;
        this.mFlags = flags;
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
                if(~(mFlags & DISPLAY_DELETED) == DISPLAY_DELETED){
                    if(currentData.getIsDeleted()){
                        holder.hide();
                        return;
                    }
                }else if((mFlags & DISPLAY_DELETED) == DISPLAY_DELETED){
                    if(!currentData.getIsDeleted()){
                        holder.hide();
                        return;
                    }
                }

                holder.bind(currentData, position);

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

    public void setOnClickListener(OnClickListener clickListener){
        this.mClickListener = clickListener;
    }


    public interface OnClickListener {
        void onClick(int rvPosition, int dbPosition);
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
    }
}
