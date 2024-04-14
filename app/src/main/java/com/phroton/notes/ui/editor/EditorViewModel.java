package com.phroton.notes.ui.editor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditorViewModel extends ViewModel {
    private MutableLiveData<String> mTitle;
    private MutableLiveData<String> mContent;

    public EditorViewModel(){
        mTitle = new MutableLiveData<>();
        mContent = new MutableLiveData<>();
    }

    public EditorViewModel(String title, String content){
        mTitle = new MutableLiveData<>(title);
        mContent = new MutableLiveData<>(content);
    }

    public String getContent(){
        return mContent.getValue();
    }

    public String getTitle(){
        return mTitle.getValue();
    }

    public void setContent(String val){
        mContent.setValue(val);
    }

    public void setTitle(String val){
        mTitle.setValue(val);
    }
}