package com.phroton.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class TaggedNote {
    @ColumnInfo(name = "note_id")
    public int mNoteId;

    @ColumnInfo(name = "tag_id")
    public int mTagId;

}
