package com.meokg456.note.viewmodel

import android.app.Notification
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meokg456.note.R
import com.meokg456.note.model.Note
import com.meokg456.note.repository.INoteRepository
import com.meokg456.note.repository.NoteRepository
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.usecase.FormatDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor (private val addNoteUseCase: AddNoteUseCase, private val noteRepository: INoteRepository) : ViewModel() {
    var note: Note = Note()
    var isEditing = false

    fun save() {
        viewModelScope.launch {
            note.id = addNoteUseCase(note)
        }
    }

    fun saveWithBackgroundThread() {
        noteRepository.addNoteWithBackgroundThread(note) { result ->
            note.id = result
        }
    }


}