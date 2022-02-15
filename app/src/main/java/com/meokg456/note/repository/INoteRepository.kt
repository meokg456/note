package com.meokg456.note.repository

import com.meokg456.note.model.Note

interface INoteRepository {
    fun getNotes(): List<Note>
    fun getDraft(): List<Note>
    fun addNote(note: Note) : Long
}