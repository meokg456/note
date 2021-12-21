package com.meokg456.note.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meokg456.note.core.Note
import java.util.*

class NotesModel : ViewModel() {
    val notes = MutableLiveData<List<Note>>(
        listOf(
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
    )
    val drafts = MutableLiveData<List<Note>>(
        listOf(
            Note(
                id = 1,
                title = "Fragment draft",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            ),
            Note(
                id = 2,
                title = "Activity draft",
                content = "content",
                createAt = Calendar.getInstance().time,
                modifiedAt = Calendar.getInstance().time
            )
        )
    )
}