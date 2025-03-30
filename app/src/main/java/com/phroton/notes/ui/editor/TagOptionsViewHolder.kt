package com.phroton.notes.ui.editor

import android.content.Context
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.phroton.notes.R
import com.phroton.notes.Tag
import com.phroton.notes.data.taggednote.TaggedNote

class TagOptionsViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

    private var mContext : Context = context
    var root : View = itemView
    var option : CheckBox;
    var tagId : Long = 0L;

    fun bind(tag : Tag?){
        option.setText(tag?.name) ?: "null";
        tagId = tag?.getId() ?: 0L;
    }

    init {
        option = itemView.findViewById(R.id.options_tag)
    }
}