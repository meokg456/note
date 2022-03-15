package com.meokg456.note.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.meokg456.note.model.Note

@Dao
interface NoteDao : BaseDao<Note> {
    @Query("SELECT * FROM notes WHERE isDraft = 0")
    fun getNotes(): PagingSource<Int, Note>
    @Query("SELECT * FROM notes WHERE isDraft = 1")
    fun getDrafts(): PagingSource<Int, Note>
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Note
}