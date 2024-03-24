package com.phroton.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mNotes;

    NoteRepository(Application application){
        NoteRoomDatabase roomDatabase = NoteRoomDatabase.getDatabase(application);
        mNoteDao = roomDatabase.noteDao();
        mNotes = mNoteDao.getNotesByDescendingId();
    }

    public LiveData<List<Note>> getNotes(){
        return mNotes;
    }

    public void insert(Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }
}
