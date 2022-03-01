package com.meokg456.note.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel(start: Int) : ViewModel() {
    var count = MutableLiveData(start)
    fun increase() {
        count.value = count.value?.plus(1)
    }
}