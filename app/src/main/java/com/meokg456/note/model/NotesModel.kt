package com.meokg456.note.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meokg456.note.core.Note

class NotesModel : ViewModel() {
    val notes = MutableLiveData<List<Note>>()
    val drafts = MutableLiveData<List<Note>>()
}