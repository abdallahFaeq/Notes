package com.training.hilt_roomdatabase.core_data.repository

import com.training.hilt_roomdatabase.core_data.local.dao.NoteDao
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private var dao: NoteDao)
    :NoteRepository{
    override suspend fun insertNote(note: NoteEntity){dao.insertNote(note)}

    override suspend fun updateNote(note: NoteEntity) { dao.updateNote(note) }

    override suspend fun deleteNote(note: NoteEntity) {
        dao.deleteNote(note)
    }

    override suspend fun getAllNotes(): MutableList<NoteEntity> = dao.getAllNotes()

    override suspend fun getNote(id: Long): NoteEntity = dao.getNote(id)

}