package com.phroton.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<Note>> mNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NoteRepository(application);
        mNotes = mRepository.getNotes();
    }

    LiveData<List<Note>> getNotes(){
        return mNotes;
    }

    public void insert(Note note){
        mRepository.insert(note);
    }
}
