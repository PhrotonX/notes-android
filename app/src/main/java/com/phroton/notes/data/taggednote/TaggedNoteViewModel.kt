package com.phroton.notes.data.taggednote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.phroton.notes.Note

class TaggedNoteViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mRepository : TaggedNoteRepository

    fun getTaggedNotes() : LiveData<List<TaggedNote>>{
        return mRepository.taggedNotes
    }

    fun getNotesByTagId(id: Long) : LiveData<List<Note>>{
        return mRepository.getNotesByTagId(id)
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