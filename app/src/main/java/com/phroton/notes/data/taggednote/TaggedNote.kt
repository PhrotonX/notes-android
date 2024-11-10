package com.phroton.notes.data.taggednote

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tagged_note", primaryKeys = ["note_id", "tag_id"])
data class TaggedNote (
    @ColumnInfo(name = "note_id")
    var noteId: Long,
    @ColumnInfo(name = "tag_id")
    var tagId: Long
)