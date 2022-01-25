package com.meokg456.note.uistate

import com.meokg456.note.uistate.NoteUiState
import java.util.*

data class NotesUiState (
    val notes: List<NoteUiState> = listOf(),
    val drafts: List<NoteUiState> = listOf()
)