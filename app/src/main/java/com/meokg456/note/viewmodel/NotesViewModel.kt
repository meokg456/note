package com.meokg456.note.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meokg456.note.model.Note
import com.meokg456.note.usecase.AddNoteUseCase
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
    private val fetchDraftsUseCase: FetchDraftsUseCase) : ViewModel() {

    val notes = MutableStateFlow<List<Note>>(listOf())
    val drafts = MutableStateFlow<List<Note>>(listOf())

    fun fetchNotes() {
        viewModelScope.launch {
            notes.update {
                fetchNotesUseCase()
            }
        }
    }

    fun fetchDrafts() {
        viewModelScope.launch {
            drafts.update {
                fetchDraftsUseCase()
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notes.update {
                val notesList = it.toMutableList()
                notesList.add(note)
                notesList.toList()
            }
        }
    }
    fun updateNote(note: Note) {
        viewModelScope.launch {
            notes.update {
                val notesList = it.toMutableList()
                val index = notesList.indexOfFirst { element -> element.id == note.id }
                Log.d("Debug", note.title)
                notesList[index] = note
                notesList.toList()
            }
        }
    }
}