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

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "color")
    public int mColor;

    @Ignore
    private static final int mVersionCode = BuildConfig.VERSION_CODE;

    @ColumnInfo(name = "version", defaultValue = mVersionCode + "")
    public int mVersion;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "content")
    public String mContent;
    public Note(String title, String content){
        this.mTitle = title;
        this.mContent = content;
        this.mColor = 0;
    }

    public int getColor(){ return mColor; }
    public int getId(){ return id; }

    public String getTitle()
    {
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    public int getVersion(){return mVersion; }

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

    public void setTitle(String val){
        mTitle = val;
    }

    public void setContent(String val){
        mContent = val;
    }

    public void setVersion(int val) {mVersion = val;}

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
