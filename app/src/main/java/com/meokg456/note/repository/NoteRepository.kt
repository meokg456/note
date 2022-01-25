package com.meokg456.note.repository

import com.meokg456.note.datasource.NoteLocalDataSource
import com.meokg456.note.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor (private val noteLocalDataSource: NoteLocalDataSource) : INoteRepository{
    override fun getNotes(): List<Note> {
        return noteLocalDataSource.getNotes()
    }

    override fun getDraft(): List<Note> {
        return noteLocalDataSource.getDraft()
    }

}