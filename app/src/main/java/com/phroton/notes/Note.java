package com.phroton.notes;

import android.content.Intent;
import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @Ignore public static final String NOTE_ID_EXTRA = Build.ID + "NOTE_ID_EXTRA";
    @Ignore public static final String NOTE_POSITION_EXTRA = Build.ID + "NOTE_POSITION_EXTRA";
    @Ignore public static final String NOTE_TITLE_EXTRA = "NOTE_TITLE_EXTRA";
    @Ignore public static final String NOTE_CONTENT_EXTRA = "NOTE_CONTENT_EXTRA";
    @Ignore public static final String NOTE_COLOR_EXTRA = "NOTE_COLOR_EXTRA";
    @Ignore public static final String NOTE_DELETE_EXTRA = "NOTE_DELETE_EXTRA";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    public long id;

    @ColumnInfo(name = "color")
    public int mColor;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "is_archived", defaultValue = "0")
    public boolean mIsArchived = false;

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    public boolean mIsDeleted = false;

    @ColumnInfo(name = "content")
    public String mContent;

    @ColumnInfo(name = "tags") public long tag;
    public Note(String title, String content){
        this.mTitle = title;
        this.mContent = content;
        this.mColor = 0;
        this.mIsDeleted = false;
        this.mIsArchived = false;
    }

    public int getColor(){ return mColor; }
    public long getId(){ return id; }
    public String getTitle()
    {
        return mTitle;
    }
    public String getContent(){
        return mContent;
    }
    public boolean isArchived(){ return mIsArchived; }
    public boolean isDeleted(){
        return mIsDeleted;
    }

    @Ignore
    public static Intent packCurrentNote(Note note, boolean withDbId, int rvId){
        Intent intent = packCurrentNote(note, withDbId);
        intent.putExtra(NOTE_POSITION_EXTRA, rvId);
        return intent;
    }

    @Ignore
    public static Intent packCurrentNote(Note note, boolean withDbId){
        Intent intent = new Intent();

        if(withDbId){
            intent.putExtra(NOTE_ID_EXTRA, note.getId());
        }

        intent.putExtra(NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(NOTE_CONTENT_EXTRA, note.getContent());
        intent.putExtra(NOTE_COLOR_EXTRA, note.getColor());

        return intent;
    }

    public void setColor(int val){
        mColor = val;
    }

    public void setId(long val){id = val;}

    public void setIsDeleted(boolean val){
        mIsDeleted = val;
    }

    public void setTitle(String val){
        mTitle = val;
    }

    public void setContent(String val){
        mContent = val;
    }

    @Ignore
    public static Note unpackCurrentNote(Intent intent, boolean withId){
        Note note = new Note(intent.getStringExtra(NOTE_TITLE_EXTRA),
                intent.getStringExtra(NOTE_CONTENT_EXTRA));
        note.setColor(intent.getIntExtra(NOTE_COLOR_EXTRA,
                R.color.background_white));

        if(withId){
            note.setId(intent.getLongExtra(NOTE_ID_EXTRA, -1));
        }

        if(note.getColor() == 0x0) {
            note.setColor(R.color.background_white);
        }

        return note;
    }
}
