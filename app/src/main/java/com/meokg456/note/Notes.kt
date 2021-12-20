package com.meokg456.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.meokg456.note.core.Note
import com.meokg456.note.model.NotesModel

class Notes : Fragment(R.layout.fragment_notes) {
    private val notesModel : NotesModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}