package com.phroton.notes.data.taggednote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.phroton.notes.Note
import com.phroton.notes.Tag

@Entity(tableName = "tagged_note", primaryKeys = ["note_id", "tag_id"],
    foreignKeys = [
        ForeignKey(entity = Note::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("noteId"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId"),
            onDelete = ForeignKey.CASCADE)
    ]
)
data class TaggedNote (
    @ColumnInfo(name = "note_id")
    var noteId: Long = 0L,

    @ColumnInfo(name = "tag_id")
    var tagId: Long = 0L
)