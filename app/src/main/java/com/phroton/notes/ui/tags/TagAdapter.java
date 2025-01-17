package com.phroton.notes.ui.tags;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import com.phroton.notes.R;
import com.phroton.notes.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {
    private Context mContext;
    private List<Tag> mTag;

    private int mSelectedTagId = -1;

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_tag, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch(item.getItemId()){
                case R.id.menu_tag_edit:
                    Toast.makeText(mContext, "Edited: " + getSelectedTagId(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_tag_delete:
                    Toast.makeText(mContext, "Deleted: " + getSelectedTagId(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            setSelectedTagId(-1);
        }
    };

    public TagAdapter(Context context){
        mContext = context;
        mTag = new ArrayList<>();
    }

    public TagAdapter(Context context, List<Tag> tag){
        mContext = context;

        //Set the value of mTag, but use empty list if the tag list is null.
        mTag = (tag != null) ? tag : new ArrayList<>();
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = mTag.get(position);

        holder.bind(tag);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setSelectedTagId(tag.getId());
                ((AppCompatActivity)mContext).startSupportActionMode(mActionModeCallback);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mTag != null) ? mTag.size() : 0;
    }

    public int getSelectedTagId(){
        return mSelectedTagId;
    }

    public void setSelectedTagId(int position){
        mSelectedTagId = position;
    }
}
