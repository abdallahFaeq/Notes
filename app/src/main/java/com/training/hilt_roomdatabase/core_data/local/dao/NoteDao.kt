package com.training.hilt_roomdatabase.core_data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.utils.Constants

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("select * from ${Constants.NOTE_TABLE} order by ${Constants.NOTE_ID_COLUMN} desc")
    suspend fun getAllNotes():MutableList<NoteEntity>

    @Query("select * from ${Constants.NOTE_TABLE} where ${Constants.NOTE_ID_COLUMN} like :id")
    suspend fun getNote(id:Long): NoteEntity
}