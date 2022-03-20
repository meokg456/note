package com.meokg456.note.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.meokg456.note.model.Note
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.usecase.FetchDraftsUseCase
import com.meokg456.note.usecase.FetchNotesUseCase
import com.meokg456.note.usecase.FormatDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (
    fetchNotesUseCase: FetchNotesUseCase,
    fetchDraftsUseCase: FetchDraftsUseCase) : ViewModel() {

    val notes = fetchNotesUseCase(pageSize = 10).cachedIn(viewModelScope)
    val drafts = fetchDraftsUseCase(pageSize = 10).cachedIn(viewModelScope)
}