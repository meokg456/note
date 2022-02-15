package com.meokg456.note.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateToTimeStamp(date: Date): Long = date.time
    @TypeConverter
    fun timeStampToDate(time: Long): Date = Date(time)
}
