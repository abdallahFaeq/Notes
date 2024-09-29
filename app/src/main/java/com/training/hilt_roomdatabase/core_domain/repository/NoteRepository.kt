package com.training.hilt_roomdatabase.core_domain.repository

import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity

interface NoteRepository {
    suspend fun insertNote(note:NoteEntity)

    suspend fun updateNote(note:NoteEntity)

    suspend fun deleteNote(note:NoteEntity)

    suspend fun getAllNotes():MutableList<NoteEntity>

    suspend fun getNote(id:Long) : NoteEntity
}