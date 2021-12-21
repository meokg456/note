package com.meokg456.note.core

import java.io.Serializable
import java.util.*

data class Note (val id: Int, val title: String, val content: String, val createAt: Date, val modifiedAt: Date?) : Serializable {
}