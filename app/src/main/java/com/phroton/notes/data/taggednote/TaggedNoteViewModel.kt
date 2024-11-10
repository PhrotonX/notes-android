package com.phroton.notes.data.taggednote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TaggedNoteViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mRepository : TaggedNoteRepository

    fun getTaggedNotes() : LiveData<List<TaggedNote>>{
        return mRepository.taggedNotes
    }

    fun getTaggedNotesById(id: Long) : LiveData<List<TaggedNote>>{
        return mRepository.getTaggedNoteById(id)
    }

    fun delete(taggedNote: TaggedNote){
        mRepository.delete(taggedNote)
    }

    fun insert(taggedNote: TaggedNote){
        mRepository.insert(taggedNote)
    }

    fun update(taggedNote: TaggedNote){
        mRepository.update(taggedNote)
    }
}