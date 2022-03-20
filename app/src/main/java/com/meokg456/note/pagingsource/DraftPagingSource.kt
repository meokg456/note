package com.meokg456.note.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.meokg456.note.model.Note

class DraftPagingSource : PagingSource<Int, Note>() {
    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        TODO("Not yet implemented")
    }
}