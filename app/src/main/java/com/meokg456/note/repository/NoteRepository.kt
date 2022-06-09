package com.meokg456.note.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.meokg456.note.datasource.NoteLocalDataSource
import com.meokg456.note.model.Note
import com.meokg456.note.pagingsource.NotePagingSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor
import javax.inject.Inject

class NoteRepository @Inject constructor (
    private val noteLocalDataSource: NoteLocalDataSource,
                                          private val executor: Executor,
) : INoteRepository {
    override fun getNotes(pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize)
    ) {
        NotePagingSource(false, noteLocalDataSource)
    }.flow

    override fun getDraft(pageSize: Int) = Pager(
        config = PagingConfig(pageSize = pageSize)
    ) {
        NotePagingSource(true, noteLocalDataSource)
    }.flow

    override fun addNote(note: Note): Long {
        return noteLocalDataSource.addNote(note)
    }

    override fun addNoteWithBackgroundThread(note: Note, callback: (Long) -> Unit) {
        executor.execute {
            val id = addNote(note)
            callback(id)
        }
    }

    override fun getTotalNotes(): Int {
        return noteLocalDataSource.getTotalNotes()
    }

    override fun fetchTotalNotesPeriodically() {
        noteLocalDataSource.fetchTotalNotesPeriodically()
    }


}