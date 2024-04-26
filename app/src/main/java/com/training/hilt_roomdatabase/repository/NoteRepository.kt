package com.training.hilt_roomdatabase.repository

import com.training.hilt_roomdatabase.db.NoteDao
import com.training.hilt_roomdatabase.db.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private var dao: NoteDao){
    fun insertNote(note:NoteEntity) = dao.insertNote(note)
    fun updateNote(note:NoteEntity) = dao.updateNote(note)
    fun deleteNote(note:NoteEntity) = dao.deleteNote(note)
    fun getAllNotes():MutableList<NoteEntity> = dao.getAllNotes()
    fun getNote(id:Long):NoteEntity = dao.getNote(id)
}