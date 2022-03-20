package com.meokg456.note.datasource

import androidx.paging.PagingSource
import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor (private val noteDao: NoteDao, private val ioDispatcher: CoroutineDispatcher) {
    suspend fun getNotes(pageSize: Int, pageNumber: Int): List<Note> =
        withContext(ioDispatcher) {
            noteDao.getNotes(pageSize, pageSize * (pageNumber - 1))
        }

    suspend fun getDraft(pageSize: Int, pageNumber: Int): List<Note> =
        withContext(ioDispatcher) {
            noteDao.getDrafts(pageSize, pageSize * (pageNumber - 1))
        }

    fun addNote(note: Note) : Long {
        return noteDao.insert(note)
    }
}