package com.training.hilt_roomdatabase.core_domain.interactor

import com.training.hilt_roomdatabase.core_domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesInteractor @Inject constructor(
    private val noteRepository: NoteRepository
){
     suspend operator fun invoke() = noteRepository.getAllNotes()
}