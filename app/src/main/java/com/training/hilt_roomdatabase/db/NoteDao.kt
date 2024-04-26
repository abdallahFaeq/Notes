package com.training.hilt_roomdatabase.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note:NoteEntity)

    @Update
    fun updateNote(note:NoteEntity)

    @Delete
    fun deleteNote(note:NoteEntity)

    @Query("select * from note_table order by note_id desc")
    fun getAllNotes():MutableList<NoteEntity>

    @Query("select * from note_table where note_id like :id")
    fun getNote(id:Long):NoteEntity
}