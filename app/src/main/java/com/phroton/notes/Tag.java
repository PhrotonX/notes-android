package com.phroton.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tag")
public class Tag{
    @ColumnInfo(name = "tag_id")
    @PrimaryKey
    public int id;

    public Tag(){

    }

    @ColumnInfo(name = "tag_name")
    public String mName;

    @ColumnInfo(name = "tag_created_at")
    public Date mCreatedAt;

    @ColumnInfo(name = "tag_deleted_at")
    public Date mTagDeletedAt;

    @ColumnInfo(name = "tag_updated_at")
    public Date mTagUpdatedAt;

    @Ignore
    public Tag(String tagName){
        mName = tagName;
    }

}
