package com.meokg456.note.usecase

import androidx.paging.PagingData
import com.meokg456.note.model.Note
import com.meokg456.note.repository.INoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNotesUseCase @Inject constructor (private val noteRepository: INoteRepository) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Note>> =
        noteRepository.getNotes(pageSize)
}