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

    interface RepositoryCallback<T>{
        void onComplete(Result<T> result);
    }

    NoteRepository(Application application){
        NoteRoomDatabase roomDatabase = NoteRoomDatabase.getDatabase(application);
        try{
            mNoteDao = roomDatabase.noteDao();
        }catch(NullPointerException e){
            Log.e("com.phroton.notes", "Error retreiving data from NoteDao!");
            e.printStackTrace();
        }
    }

    public LiveData<List<Note>> getNotesCompat(){
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNotes = mNoteDao.getAllNotes();
        });

        return mNotes;
    }
    public void getNotes(final RepositoryCallback<LiveData<List<Note>>> callback){
        try {
            NoteRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run(){
                    try{
                        Result<LiveData<List<Note>>> result = getNotesFromDatabase();
                        callback.onComplete(result);
                    }catch(Exception e){
                        e.printStackTrace();
                        Result<LiveData<List<Note>>> errorResult = getNotesFromDatabase();
                        callback.onComplete(errorResult);
                    }
                }
            });

            //return mNoteDao.getNotesByDescendingId();

        }catch(Exception e){
            Log.d("com.phroton.notes", "Failed to retreive data from RoomDatabase");
            e.printStackTrace();
        }

    }

    private Result<LiveData<List<Note>>> getNotesFromDatabase(){
        try{
            return new Result.Success<LiveData<List<Note>>>(mNoteDao.getNotesByDescendingId());
        }catch(Exception e){
            return new Result.Error<LiveData<List<Note>>>(e);
        }
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

}
