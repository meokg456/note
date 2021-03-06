package com.meokg456.note.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.meokg456.note.model.Note

@Dao
interface NoteDao : BaseDao<Note> {
    @Query("SELECT * FROM notes WHERE isDraft = 0 LIMIT :limit OFFSET :offset")
    fun getNotes(limit: Int, offset: Int): List<Note>
    @Query("SELECT * FROM notes WHERE isDraft = 1 LIMIT :limit OFFSET :offset")
    fun getDrafts(limit: Int, offset: Int): List<Note>
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Note
    @Query("SELECT COUNT(*) FROM notes WHERE isDraft = 0")
    fun getTotalNotes(): Int
}