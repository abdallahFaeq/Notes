package com.training.hilt_roomdatabase.core_data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.training.hilt_roomdatabase.core_data.local.dao.NoteDao
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}