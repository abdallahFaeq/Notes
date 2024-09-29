package com.training.hilt_roomdatabase.core_domain.interactor

import com.training.hilt_roomdatabase.core_domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdInteractor @Inject constructor(
    private val noteRepository: NoteRepository
){
    suspend operator fun invoke(id:Long) = noteRepository.getNote(id)
}