package com.phroton.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete
    void delete(Note note);

//    @Query("DELETE FROM notes")
//    void deleteAll();

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<Note>> getNotesByDescendingId();
}
