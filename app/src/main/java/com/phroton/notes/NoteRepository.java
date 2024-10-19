package com.phroton.notes;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.lang.NullPointerException;
import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mNotes;

    NoteRepository(Application application){
        NoteRoomDatabase roomDatabase = NoteRoomDatabase.getDatabase(application);
        mNoteDao = roomDatabase.noteDao();

        mNotes = mNoteDao.getAllNotes();
    }

    public LiveData<List<Note>> getNotesCompat(){
        return mNotes;
    }

    public LiveData<Note> getNote(long id){
        return mNoteDao.getNote(id);
    }

    public void insert(Note note){
        try{
            NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
                mNoteDao.insert(note);
            });
        }catch(NullPointerException e){
            Log.e("com.phroton.notes", "NoteDao is null!");
            e.printStackTrace();
        }
    }

    public void update(Note note){
        try{
            NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
                mNoteDao.update(note);
            });
        }catch(NullPointerException e){
            Log.e("com.phroton.notes", "NoteDao is null!");
            e.printStackTrace();
        }
    }

    public void markAsArchived(long id, boolean isArchived){
        try{
            NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
                mNoteDao.markAsArchived(id, isArchived);
            });
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void markAsDeleted(long id, boolean isDeleted){
        try{
            NoteRoomDatabase.databaseWriteExecutor.execute(() ->{
                mNoteDao.markAsDeleted(id, isDeleted);
            });
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    public void delete(Note note){
        try{
            NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
                mNoteDao.delete(note);
            });
        }catch(NullPointerException e){
            Log.e("com.phroton.notes", "NoteDao is null!");
            e.printStackTrace();
        }
    }

    public LiveData<List<Note>> search(String query, String color){
        LiveData<List<Note>> data;

        try{
            data = mNoteDao.search(query, color);
            return data;
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        return null;
    }
}
