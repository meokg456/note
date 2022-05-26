package com.meokg456.note.datasource

import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import com.meokg456.note.workers.FetchNoteTotalWorker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NoteLocalDataSource @Inject constructor (
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager,
) {
    suspend fun getNotes(pageSize: Int, pageNumber: Int): List<Note> =
        withContext(ioDispatcher) {
            noteDao.getNotes(pageSize, pageSize * (pageNumber - 1))
        }

    suspend fun getDraft(pageSize: Int, pageNumber: Int): List<Note> =
        withContext(ioDispatcher) {
            noteDao.getDrafts(pageSize, pageSize * (pageNumber - 1))
        }

    fun addNote(note: Note): Long {
        return noteDao.insert(note)
    }

    fun getTotalNotes() : Int {
        return noteDao.getTotalNotes()
    }

    fun fetchTotalNotesPeriodically() {
//        val fetchNewsRequest = PeriodicWorkRequestBuilder<FetchNoteTotalWorker>(
//            MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS
//        )
//            .addTag(TOTAL_NOTE_TAG)
//
//        workManager.enqueueUniquePeriodicWork(
//            TOTAL_NOTE,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            fetchNewsRequest.build()
//        )
    }

    fun cancelFetchingNewsPeriodically() {
        workManager.cancelAllWorkByTag(TOTAL_NOTE_TAG)
    }

    companion object {
        const val TOTAL_NOTE_TAG = "Total notes"
        const val TOTAL_NOTE = "Total notes count"
        const val REFRESH_RATE_MINUTES = 15L
    }
}