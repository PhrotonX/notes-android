package com.phroton.notes.data.taggednote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.phroton.notes.Note
import com.phroton.notes.Tag

@Entity(tableName = "tagged_note", primaryKeys = ["note_id", "tag_id"],
    foreignKeys = [
        ForeignKey(entity = Note::class,
            parentColumns = arrayOf("note_id"),
            childColumns = arrayOf("note_id"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Tag::class,
            parentColumns = arrayOf("tag_id"),
            childColumns = arrayOf("tag_id"),
            onDelete = ForeignKey.CASCADE)
    ]
)
class TaggedNote {
    constructor(){
        this.tagId = 0L
        this.noteId = 0L
    }

    @Ignore
    constructor(tag: Tag, note: Note){
        this.tagId = tag.getId()
        this.noteId = note.getId()
    }

    @ColumnInfo(name = "note_id")
    var noteId: Long = 0L

    @ColumnInfo(name = "tag_id")
    var tagId: Long = 0L
}