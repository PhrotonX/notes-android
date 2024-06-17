package com.phroton.notes;

import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @Ignore
    public static final String NOTE_ID_EXTRA = Build.ID + ".NOTE_ID_EXTRA";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "color")
    public int mColor;

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

    public void setColor(int val){
        mColor = val;
    }

    public void setTitle(String val){
        mTitle = val;
    }

    public void setContent(String val){
        mContent = val;
    }
}
