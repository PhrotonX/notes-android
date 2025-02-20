package com.phroton.notes.data.taggednote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phroton.notes.Note
import com.phroton.notes.Tag
import com.phroton.notes.data.taggednote.TaggedNote

@Dao
interface TaggedNoteDao {
    @Delete
    fun delete(taggedNote: TaggedNote)

    @Query("DELETE FROM tagged_note")
    fun deleteAll();

    @Query("SELECT * FROM tagged_note")
    fun getAllTaggedNotes() : LiveData<List<TaggedNote>>

    @Query("SELECT * FROM notes JOIN tagged_note ON notes.note_id = tagged_note.note_id WHERE tagged_note.tag_id =:id")
    fun getNotesByTagId(id: Long) : LiveData<List<Note>>

    @Query("SELECT * FROM tag JOIN tagged_note ON tag.tag_id = tagged_note.tag_id WHERE tagged_note.note_id = :id")
    fun getTagsByNoteId(id: Long) : LiveData<List<Tag>>

    @Insert
    fun insert(taggedNote: TaggedNote)

    @Query("DELETE FROM tagged_note WHERE note_id =:noteId AND tag_id =:tagId")
    fun remove(noteId: Long, tagId: Long)

    @Update
    fun update(taggedNote: TaggedNote)
}