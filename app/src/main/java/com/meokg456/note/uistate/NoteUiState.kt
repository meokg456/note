package com.meokg456.note.uistate

import java.io.Serializable
import java.util.*

data class NoteUiState (val id: Int, val title: String?, val content: String?, val createAt: String, val modifiedAt: String?, val isDraft: Boolean = false) {
}