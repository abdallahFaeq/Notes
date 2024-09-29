package com.training.hilt_roomdatabase.core_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_domain.interactor.GetAllNotesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesViewModel @Inject constructor(
    private val getAllNotesInteractor: GetAllNotesInteractor
):ViewModel() {
    private var _notesState : MutableStateFlow<List<NoteEntity>> = MutableStateFlow(emptyList())
    val notesState = _notesState.asStateFlow()

    fun getAllNotes(){
        viewModelScope
            .launch {
                _notesState.emit(getAllNotesInteractor())
            }
    }
}