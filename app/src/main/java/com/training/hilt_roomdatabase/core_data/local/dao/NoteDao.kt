package com.training.hilt_roomdatabase.core_data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("select * from note_table order by note_id desc")
    suspend fun getAllNotes():MutableList<NoteEntity>

    @Query("select * from note_table where note_id like :id")
    suspend fun getNote(id:Long): NoteEntity
}