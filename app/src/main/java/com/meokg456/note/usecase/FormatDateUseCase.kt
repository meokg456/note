package com.meokg456.note.usecase

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FormatDateUseCase @Inject constructor() {
    private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
    operator fun invoke(date: Date?): String? {
        if(date == null) return null
        return formatter.format(date)
    }
}