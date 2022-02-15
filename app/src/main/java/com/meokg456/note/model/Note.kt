package com.meokg456.note.model

import androidx.room.*
import com.meokg456.note.database.Converters
import java.io.Serializable
import java.util.*

@Entity(tableName = "notes")
@TypeConverters(Converters::class)
data class Note (@PrimaryKey(autoGenerate = true) var id: Long = 0, var title: String = "", var content: String = "", var createAt: Date = Date(), var modifiedAt: Date? = null, val isDraft: Boolean = false) : Serializable {
    @Ignore @Transient var onTap: () -> Unit = {}
}