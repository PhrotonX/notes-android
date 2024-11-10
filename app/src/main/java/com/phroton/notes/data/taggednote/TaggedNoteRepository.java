package com.phroton.notes.data.taggednote;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.phroton.notes.NoteRoomDatabase;

import java.util.List;

public class TaggedNoteRepository {
    private TaggedNoteDao mTaggedNoteDao;
    private LiveData<List<TaggedNote>> mTaggedNotes;

    private final NoteRoomDatabase mDatabase;

    TaggedNoteRepository(Application application){
        mDatabase = NoteRoomDatabase.getDatabase(application);
        mTaggedNoteDao = mDatabase.taggedNoteDao();

        mTaggedNotes = mTaggedNoteDao.getAllTaggedNotes();

    }

    public LiveData<List<TaggedNote>> getTaggedNotes(){
        return mTaggedNotes;
    }

    public LiveData<List<TaggedNote>> getTaggedNoteById(long id){
        return mTaggedNoteDao.getTaggedNotesByTagId(id);
    }

    public void update(TaggedNote taggedNote){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTaggedNoteDao.update(taggedNote));
    }

    public void delete(TaggedNote taggedNote){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTaggedNoteDao.delete(taggedNote));
    }

    public void insert(TaggedNote taggedNote){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mTaggedNoteDao.insert(taggedNote));
    }
}
