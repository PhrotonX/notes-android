package com.phroton.notes.ui.tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phroton.notes.R;
import com.phroton.notes.Tag;

import java.util.ArrayList;

public class TagListAdapter extends ArrayAdapter<Tag> {
    public TagListAdapter(Context context, ArrayList<Tag> tagList){
        super(context, 0, tagList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater.from(getContext()).inflate(R.layout.tag, parent, false);
        }

        Tag tag = getItem(position);

        TextView textView = convertView.findViewById(R.id.tag_name);

        if(tag != null) textView.setText(tag.mName);

        return convertView;
    }
}
