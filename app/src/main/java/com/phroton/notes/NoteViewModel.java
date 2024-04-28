package com.phroton.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    //private final LiveData<List<Note>> mNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NoteRepository(application);
        //mNotes = mRepository.getNotesCompat();
    }

    /*public LiveData<List<Note>> getNotes(){
        mRepository.getNotes(new NoteRepository.RepositoryCallback<LiveData<List<Note>>>(){
            @Override
            public void onComplete(Result<LiveData<List<Note>>> result){
                if(result instanceof Result.Success){
                    mNotes = ((Result.Success<LiveData<List<Note>>>) result).data;
                }else{
                    ((Result.Error<LiveData<List<Note>>>) result).exception.printStackTrace();
                }
            }
        });

        return mNotes;
    }*/

    public LiveData<List<Note>> getNotesCompat(){

        return mRepository.getNotesCompat();

    }

    public void insert(Note note){
        mRepository.insert(note);
    }
}
