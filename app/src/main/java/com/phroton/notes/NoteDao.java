package com.phroton.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE note_id = :id")
    LiveData<Note> getNote(long id);

    @Query("SELECT * FROM notes ORDER BY note_id DESC")
    LiveData<List<Note>> getNotesByDescendingId();

    @Update
    void update(Note note);

    @Query("UPDATE notes SET is_archived = :isArchived WHERE note_id = :id")
    void markAsArchived(long id, boolean isArchived);

    @Query("UPDATE notes SET is_deleted =:isDeleted WHERE note_id=:id")
    void markAsDeleted(long id, boolean isDeleted);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes WHERE title LIKE :content OR content LIKE :content AND color LIKE :color")
    LiveData<List<Note>> search(String content, String color);
}
