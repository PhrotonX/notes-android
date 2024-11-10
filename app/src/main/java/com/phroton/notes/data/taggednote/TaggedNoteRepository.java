package com.phroton.notes.data.taggednote;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.phroton.notes.NoteRoomDatabase;

import java.util.List;

public class TaggedNoteRepository {
    private TaggedNote mTaggedNoteDao;
    private LiveData<List<TaggedNote>> mTaggedNotes;

    public TaggedNoteRepository(Application application){
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mTaggedNoteDao = database.taggedNoteDao();


    }
}
