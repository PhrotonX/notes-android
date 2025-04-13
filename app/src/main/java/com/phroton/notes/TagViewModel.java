package com.phroton.notes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private TagRepository mRepository;
    private final LiveData<List<Tag>> mTags;
    public TagViewModel(Application application){
        super(application);

        mRepository = new TagRepository(application);
        mTags = mRepository.getAllTags();
    }

    public void createTag(String tagName){
        mRepository.insert(new Tag(tagName));
    }

    public LiveData<List<Tag>> getTags(){ return mTags; }

    public LiveData<Tag> getTag(long id){
        return mRepository.getTag(id);
    }

    public void delete(Tag tag){
        mRepository.delete(tag);
    }

    public void insert(Tag tag){
        mRepository.insert(tag);
    }

    public void update(Tag tag){
        mRepository.update(tag);
    }

}
