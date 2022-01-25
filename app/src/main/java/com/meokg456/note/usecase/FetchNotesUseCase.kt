package com.meokg456.note.usecase

import com.meokg456.note.model.Note
import com.meokg456.note.repository.INoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNotesUseCase @Inject constructor (private val noteRepository: INoteRepository, private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    suspend operator fun invoke(): List<Note> = (withContext(defaultDispatcher) {
        noteRepository.getNotes()
    })
}