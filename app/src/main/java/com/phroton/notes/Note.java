package com.phroton.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "content")
    public String mContent;
    public Note(String title, String content){
        this.mTitle = title;
        this.mContent = content;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    public void setTitle(String val){
        mTitle = val;
    }

    public void setContent(String val){
        mContent = val;
    }
}
