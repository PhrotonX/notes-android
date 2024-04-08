package com.phroton.notes;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import java.lang.NullPointerException;
import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;

    NoteRepository(Application application){
        NoteRoomDatabase roomDatabase = NoteRoomDatabase.getDatabase(application);
        try{
            mNoteDao = roomDatabase.noteDao();
        }catch(NullPointerException e){
            Log.e("com.phroton.notes", "Error retreiving data from NoteDao!");
            e.printStackTrace();
        }
    }

    public LiveData<List<Note>> getNotes(){
        try {
            /*NoteRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run(){

                }
            });*/

            return mNoteDao.getNotesByDescendingId();

        }catch(Exception e){
            Log.d("com.phroton.notes", "Failed to retreive data from RoomDatabase");
            e.printStackTrace();
        }

        return null;
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
