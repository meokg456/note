package com.meokg456.note.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BackgroundTaskViewModel : ViewModel() {
    val timeInMillis = MutableLiveData<Long>(0)
}