package com.phroton.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private final LiveData<List<Note>> mNotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new NoteRepository(application);
        mNotes = mRepository.getNotesCompat();
    }

    //@TODO: Create getNote(int noteId) method here...

    public LiveData<List<Note>> getNotesCompat(){

        return mNotes;

    }

    public void insert(Note note){
        mRepository.insert(note);
    }

    public void update(Note note){
        mRepository.update(note);
    }

    public void markAsDeleted(int id, boolean isDeleted){
        mRepository.markAsDeleted(id, isDeleted);
    }
    public void delete(Note note){
        mRepository.delete(note);
    }
}
