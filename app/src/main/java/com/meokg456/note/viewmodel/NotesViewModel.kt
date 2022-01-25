package com.meokg456.note.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meokg456.note.model.Note
import com.meokg456.note.uistate.NoteUiState
import com.meokg456.note.uistate.NotesUiState
import com.meokg456.note.usecase.FetchDraftsUseCase
import com.meokg456.note.usecase.FetchNotesUseCase
import com.meokg456.note.usecase.FormatDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (
    private val fetchNotesUseCase: FetchNotesUseCase,
    private val fetchDraftsUseCase: FetchDraftsUseCase,
    private val formatDateUseCase: FormatDateUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())

    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun fetchNotes() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(notes = fetchNotesUseCase().map { note ->
                    NoteUiState(
                        id = note.id,
                        title = note.title,
                        content = note.content,
                        createAt = formatDateUseCase(note.createAt),
                        modifiedAt = formatDateUseCase(note.modifiedAt)
                    )
                })
            }
        }
    }

    fun fetchDrafts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(drafts = fetchDraftsUseCase().map { note ->
                    NoteUiState(
                        id = note.id,
                        title = note.title,
                        content = note.content,
                        createAt = formatDateUseCase(note.createAt),
                        modifiedAt = formatDateUseCase(note.modifiedAt)
                    )
                })
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            _uiState.update {
                val notes = _uiState.value.notes.toMutableList()
                notes.add(
                    NoteUiState(
                        id = note.id,
                        title = note.title,
                        content = note.content,
                        createAt = formatDateUseCase(note.createAt),
                        modifiedAt = formatDateUseCase(note.modifiedAt)
                    )
                )
                it.copy(notes = notes)
            }
        }
    }
}