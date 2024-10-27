package com.phroton.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TagRepository {
    private TagDao mTagDao;
    private LiveData<List<Tag>> mTags;
    public TagRepository(Application application){
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mTagDao = database.tagDao();

        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTags = mTagDao.getAllTags();
        });

    }

    public void delete(Tag tag){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTagDao.delete(tag));
    }

    public void insert(Tag tag){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTagDao.insert(tag));
    }

    public LiveData<List<Tag>> getAllTags(){
        return mTags;
    }

    public LiveData<Tag> getTag(long id){
        return mTagDao.getTag(id);
    }

    public void update(Tag tag){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTagDao.update(tag));
    }
}
