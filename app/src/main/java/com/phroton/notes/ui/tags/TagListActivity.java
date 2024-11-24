package com.phroton.notes.ui.tags;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.phroton.notes.NoteViewModel;
import com.phroton.notes.Tag;
import com.phroton.notes.TagViewModel;

import java.util.ArrayList;
import java.util.List;

public class TagListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);

        TagViewModel tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);

        tagViewModel.getTags().observe((LifecycleOwner) getApplicationContext(), new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tags) {
                TagListAdapter tagListAdapter = new TagListAdapter(getApplicationContext(), new ArrayList<>(tags));
            }
        });
    }
}
