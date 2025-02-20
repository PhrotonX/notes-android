package com.phroton.notes.data.taggednote;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.phroton.notes.Note;
import com.phroton.notes.NoteRoomDatabase;
import com.phroton.notes.Tag;

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

    public LiveData<List<Note>> getNotesByTagId(long id){
        return mTaggedNoteDao.getNotesByTagId(id);
    }

    public LiveData<List<Tag>> getTagsByNoteId(long id){
        return mTaggedNoteDao.getTagsByNoteId(id);
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
