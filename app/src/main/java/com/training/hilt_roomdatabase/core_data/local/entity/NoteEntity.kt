package com.training.hilt_roomdatabase.core_data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.training.hilt_roomdatabase.utils.Constants

@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.NOTE_ID_COLUMN)
    var id:Long = 0,
    @ColumnInfo(name = Constants.TITLE_COLUMN)
    var title:String = "",
    @ColumnInfo(name = Constants.SUBTITLE_COLUMN)
    var subtitle:String = "",
    @ColumnInfo(name = Constants.DESCRIPTION_COLUMN)
    var desc : String = "",
    @ColumnInfo(name = Constants.DATE_TIME_COLUMN)
    var dateTime:String = "",
    @ColumnInfo(name = Constants.COLOR_COLUMN)
    var color : Int?=null,
    @ColumnInfo(name = Constants.IMAGE_PATH_COLUMN)
    var imagePath :String = "",
    @ColumnInfo(name = Constants.WEB_LINK_COLUMN)
    var webLink:String = ""
)