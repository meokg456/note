package com.meokg456.note.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.meokg456.note.datasource.NoteLocalDataSource
import com.meokg456.note.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor (private val noteLocalDataSource: NoteLocalDataSource) : INoteRepository{
    override fun getNotes(pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize)
    ) {
        noteLocalDataSource.getNotes()
    }.flow

    override fun getDraft(pageSize: Int) = Pager(
    config = PagingConfig(pageSize = pageSize)
    ) {
        noteLocalDataSource.getDraft()
    }.flow
    override fun addNote(note: Note) : Long {
        return noteLocalDataSource.addNote(note)
    }

}