package com.training.hilt_roomdatabase.core_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_domain.interactor.DeleteNoteInteractor
import com.training.hilt_roomdatabase.core_domain.interactor.GetNoteByIdInteractor
import com.training.hilt_roomdatabase.core_domain.interactor.InsertNoteInteractor
import com.training.hilt_roomdatabase.core_domain.interactor.UpdateNoteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val insertNoteInteractor:InsertNoteInteractor,
    private val updateNoteInteractor: UpdateNoteInteractor,
    private val deleteNoteInteractor: DeleteNoteInteractor,
    private val getNoteInteractor: GetNoteByIdInteractor
):ViewModel(){

    private var _noteState = MutableStateFlow<NoteEntity?>(null)
    val noteState = _noteState.asStateFlow()


    fun insertNote(
        note:NoteEntity
    ){
        viewModelScope
            .launch {
                insertNoteInteractor(note)
            }
    }

    fun updateNote(
        note:NoteEntity
    ){
        viewModelScope
            .launch{
                updateNoteInteractor(note)
            }
    }

    fun deleteNote(
        note:NoteEntity
    ){
        viewModelScope
            .launch {
                deleteNoteInteractor(note)
            }
    }

    fun getNote(
        id:Long
    ){
        viewModelScope
            .launch {

                _noteState.emit(getNoteInteractor(id))
            }
    }
}