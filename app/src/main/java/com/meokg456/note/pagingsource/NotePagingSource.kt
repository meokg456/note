package com.meokg456.note.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meokg456.note.datasource.NoteLocalDataSource
import com.meokg456.note.model.Note
import java.lang.Exception

class NotePagingSource(private val isDraft: Boolean, private val dataSource: NoteLocalDataSource) : PagingSource<Int, Note>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        return try {
            val nextKey = params.key ?: 1
            Log.d("debug", params.loadSize.toString())
            val data = if(isDraft) dataSource.getDraft(params.loadSize, nextKey) else dataSource.getNotes(params.loadSize, nextKey)

            LoadResult.Page(
                data,
                null,
                if(data.isEmpty()) null else nextKey + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}