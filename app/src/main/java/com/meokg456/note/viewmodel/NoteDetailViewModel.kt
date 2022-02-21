package com.meokg456.note.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meokg456.note.model.Note
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.usecase.FormatDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor (private val addNoteUseCase: AddNoteUseCase) : ViewModel() {
    var note: Note = Note()
    var isEditing = false

    fun save() {
        runBlocking {
            note.id = addNoteUseCase(note)
        }
    }
}