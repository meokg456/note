package com.meokg456.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.meokg456.note.model.NotesModel


class Draft : Fragment(R.layout.fragment_draft) {
    private val notesModel : NotesModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView? = view.findViewById(R.id.note_list)
        if(recyclerView?.adapter == null) {
            val noteAdapter = notesModel.drafts.value?.let { NoteAdapter(it) }
            recyclerView?.adapter = noteAdapter
        }
    }

}