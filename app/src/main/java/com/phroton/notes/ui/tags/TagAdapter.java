package com.phroton.notes.ui.tags;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
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

    private OnContextItemSelected onContextItemSelected;

    private int selectedPosition = -1;

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
                holder.setSelectedTagId(tag.getId());
                setSelectedPosition(holder.getBindingAdapterPosition());

                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_tag, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onContextItemSelected.onContextItemSelected(item, tag);
                    }
                });

                popupMenu.show();

                return true;
            }
        });

        holder.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                ((AppCompatActivity)mContext).getMenuInflater().inflate(R.menu.menu_tag, menu);
                //menu.add(0, R.id.menu_tag_edit, 10, R.string.edit_tag);
                //menu.add(0, R.id.menu_tag_delete, 20, R.string.delete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mTag != null) ? mTag.size() : 0;
    }

    public int getSelectedPosition(){
        return this.selectedPosition;
    }

    public void setOnContextItemSelected(OnContextItemSelected onContextItemSelected){
        this.onContextItemSelected = onContextItemSelected;
    }

    public void setSelectedPosition(int position){
        this.selectedPosition = position;
    }

    public interface OnContextItemSelected{
        boolean onContextItemSelected(@NonNull MenuItem item, Tag tag);
    }
}
