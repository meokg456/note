package com.meokg456.note.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CountViewModelFactory constructor(private val start: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountViewModel::class.java)) {
            return CountViewModel(start) as T
        }
        throw IllegalArgumentException("ViewModel not found")

    }
}