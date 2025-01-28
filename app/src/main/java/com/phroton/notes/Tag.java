package com.phroton.notes;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.compose.material.icons.sharp.CreateKt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tag")
public class Tag implements Parcelable {
    @Ignore
    public static final String EXTRA_TAG = Build.ID + "EXTRA_TAG";
    public static final String EXTRA_TAG_ID = Build.ID + "EXTRA_TAG_ID";

    @ColumnInfo(name = "tag_id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    public Tag(){

    }

    @Ignore
    public Tag(Parcel in){
        this.id = in.readInt();
        this.mName = in.readString();
        this.mCreatedAt = (Date)in.readValue(Date.class.getClassLoader());
        this.mTagDeletedAt = (Date)in.readValue(Date.class.getClassLoader());
        this.mTagUpdatedAt = (Date)in.readValue(Date.class.getClassLoader());
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

    public String getName(){
        return mName;
    }

    public int getId(){
        return id;
    }

    public Date getCreatedAt(){
        return mCreatedAt;
    }

    public Date getTagDeletedAt(){
        return mTagDeletedAt;
    }

    public Date getTagUpdateAt(){
        return mTagUpdatedAt;
    }

    public void setName(String val){
        mName = val;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCreatedAt(Date val){
        mCreatedAt = val;
    }

    public void setTagDeletedAt(Date val){
        mTagDeletedAt = val;
    }

    public void setmTagUpdatedAt(Date val){
        mTagUpdatedAt = val;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mName);
        dest.writeValue(mCreatedAt);
        dest.writeValue(mTagDeletedAt);
        dest.writeValue(mTagUpdatedAt);
    }

    @Ignore
    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}
