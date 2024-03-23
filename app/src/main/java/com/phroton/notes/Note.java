package com.phroton.notes;

public class Note {
    public String mTitle;
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
