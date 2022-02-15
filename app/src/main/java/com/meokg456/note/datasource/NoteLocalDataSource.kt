package com.meokg456.note.datasource

import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor (private val noteDao: NoteDao) {
    fun getNotes(): List<Note> {
        return noteDao.getNotes()
    }
    fun getDraft(): List<Note> {
        return noteDao.getDrafts()
    }
    fun addNote(note: Note) : Long {
        return noteDao.insert(note)
    }
}