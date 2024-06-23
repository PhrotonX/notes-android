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
    @Ignore public static final String NOTE_TITLE_EXTRA = "NOTE_TITLE_EXTRA";
    @Ignore public static final String NOTE_CONTENT_EXTRA = "NOTE_CONTENT_EXTRA";
    @Ignore public static final String NOTE_COLOR_EXTRA = "NOTE_COLOR_EXTRA";
    @Ignore public static final String NOTE_DELETE_EXTRA = "NOTE_DELETE_EXTRA";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "color")
    public int mColor;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "is_deleted", defaultValue = "0")
    public boolean mIsDeleted = false;

    @ColumnInfo(name = "content")
    public String mContent;
    public Note(String title, String content){
        this.mTitle = title;
        this.mContent = content;
        this.mColor = 0;
        this.mIsDeleted = false;
    }

    public int getColor(){ return mColor; }
    public int getId(){ return id; }

    public boolean getIsDeleted(){
        return mIsDeleted;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    @Ignore
    public static Intent packCurrentNote(Note note, boolean withId){
        Intent intent = new Intent();

        if(withId){
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

    public void setId(int val){id = val;}

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
            note.setId(intent.getIntExtra(NOTE_ID_EXTRA, -1));
        }

        if(note.getColor() == 0x0) {
            note.setColor(R.color.background_white);
        }

        return note;
    }
}
