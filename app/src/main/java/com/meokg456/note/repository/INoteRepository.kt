package com.meokg456.note.repository

import androidx.paging.PagingData
import com.meokg456.note.model.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getNotes(pageSize: Int): Flow<PagingData<Note>>
    fun getDraft(pageSize: Int): Flow<PagingData<Note>>
    fun addNote(note: Note) : Long
}