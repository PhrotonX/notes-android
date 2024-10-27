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
public interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tag tag);

    @Query("SELECT * FROM TAG")
    LiveData<List<Tag>> getAllTags();

    @Query("SELECT * FROM TAG WHERE TAG_ID = :id")
    LiveData<Tag> getTag(long id);

    @Delete
    void delete(Tag tag);

    @Query("DELETE FROM TAG")
    void deleteAll();

    @Update
    void update(Tag tag);
}
