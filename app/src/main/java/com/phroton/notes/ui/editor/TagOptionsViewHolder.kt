package com.phroton.notes.ui.editor

import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.phroton.notes.Tag
import com.phroton.notes.data.taggednote.TaggedNote

class TagOptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var root : View;
    lateinit var option : CheckBox;
    var tagId : Long = 0L;

    fun bind(tag : Tag){
        option.setText(tag.name)
        tagId = tag.getId()
    }
}