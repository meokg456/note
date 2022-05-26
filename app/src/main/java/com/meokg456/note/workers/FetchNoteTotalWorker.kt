package com.meokg456.note.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.meokg456.note.repository.INoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltWorker
class FetchNoteTotalWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val noteRepository: INoteRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        setProgress(workDataOf(Progress to 0))
        delay(1000L)
        setProgress(workDataOf(Progress to 36))
        delay(1000L)
        setProgress(workDataOf(Progress to 100))
        return Result.success(workDataOf(Total to noteRepository.getTotalNotes()))
    }

    companion object {
        const val Progress = "Progress"
        const val Total = "Total"
    }
}