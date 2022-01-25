package com.meokg456.note.datasource

import com.meokg456.note.model.Note
import com.meokg456.note.uistate.NoteUiState
import java.util.*
import javax.inject.Inject

class NoteDAO @Inject constructor() {
    fun getNotes(): List<Note> {
        return listOf(
            Note(
                id = 1,
                title = "Fragment",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            ),
            Note(
                id = 2,
                title = "Activity",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )
    }
    fun getDraft(): List<Note> {
        return listOf(
            Note(
                id = 1,
                title = "Fragment Draft",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            ),
            Note(
                id = 2,
                title = "Activity Draft",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )
    }
}