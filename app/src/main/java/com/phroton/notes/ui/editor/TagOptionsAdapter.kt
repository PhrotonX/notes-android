package com.phroton.notes.ui.editor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phroton.notes.R
import com.phroton.notes.Tag
import com.phroton.notes.data.taggednote.TaggedNote

class TagOptionsAdapter : RecyclerView.Adapter<TagOptionsViewHolder> {
    private lateinit var mContext : Context;
    private var mTags : List<Tag>?;
    private var mTaggedNote: TaggedNote?;

    constructor(tags: List<Tag>?, taggedNote: TaggedNote?){
        mTags = tags;
        mTaggedNote = taggedNote;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagOptionsViewHolder {
        mContext = parent.context;

        val inflater = LayoutInflater.from(mContext);
        val view = inflater.inflate(R.layout.options_tag, parent, false);

        return TagOptionsViewHolder(view, mContext);
    }

    override fun getItemCount(): Int {
        return mTags?.size ?: 0
    }

    override fun onBindViewHolder(holder: TagOptionsViewHolder, position: Int) {
        holder.bind(mTags?.get(position));
    }
}