package com.training.hilt_roomdatabase.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.training.hilt_roomdatabase.utils.Constants

@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id:Long = 0,
    @ColumnInfo(name = "title_row")
    var title:String = "",
    @ColumnInfo(name = "desc_row")
    var desc : String = ""
)