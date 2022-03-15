package com.meokg456.note.datasource

import androidx.paging.PagingSource
import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor (private val noteDao: NoteDao) {
    fun getNotes(): PagingSource<Int, Note> {
        return noteDao.getNotes()
    }
    fun getDraft(): PagingSource<Int, Note> {
        return noteDao.getDrafts()
    }
    fun addNote(note: Note) : Long {
        return noteDao.insert(note)
    }
}