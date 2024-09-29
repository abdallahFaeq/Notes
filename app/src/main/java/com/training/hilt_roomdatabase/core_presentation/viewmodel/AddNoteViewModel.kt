package com.training.hilt_roomdatabase.core_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_domain.interactor.InsertNoteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val insertNoteInteractor:InsertNoteInteractor
):ViewModel(){
    fun insertNote(
        note:NoteEntity
    ){
        viewModelScope
            .launch {
                insertNoteInteractor(note)
            }
    }
}