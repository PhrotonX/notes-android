package com.phroton.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NoteRepository(application);
    }

    public LiveData<List<Note>> getNotes(){
        return mRepository.getNotes();
    }

    public void insert(Note note){
        mRepository.insert(note);
    }
}
