package com.meokg456.note.usecase

import com.meokg456.note.model.Note
import com.meokg456.note.repository.INoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val noteRepository: INoteRepository, private val defaultDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(note: Note) : Long = withContext(defaultDispatcher) {
        return@withContext noteRepository.addNote(note)
    }
}