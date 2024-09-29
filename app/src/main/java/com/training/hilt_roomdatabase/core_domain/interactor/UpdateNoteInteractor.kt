package com.training.hilt_roomdatabase.core_domain.interactor

import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteInteractor @Inject constructor(
    private val noteRepository: NoteRepository
){
    suspend operator fun invoke(note:NoteEntity) {
        noteRepository.updateNote(note)
    }
}