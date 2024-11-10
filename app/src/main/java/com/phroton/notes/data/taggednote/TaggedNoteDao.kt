package com.phroton.notes.data.taggednote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phroton.notes.data.taggednote.TaggedNote

@Dao
interface TaggedNoteDao {
    @Delete
    fun delete(taggedNote: TaggedNote)

    @Query("SELECT * FROM tagged_note")
    fun getAllTaggedNotes() : LiveData<List<TaggedNote>>

    @Query("SELECT * FROM tagged_note WHERE tag_id =:id")
    fun getTaggedNotesByTagId(id: Long) : LiveData<List<TaggedNote>>

    @Insert
    fun insert(taggedNote: TaggedNote)

    @Query("DELETE FROM tagged_note WHERE note_id =:noteId AND tag_id =:tagId")
    fun remove(noteId: Long, tagId: Long)

    @Update
    fun update(taggedNote: TaggedNote)
}