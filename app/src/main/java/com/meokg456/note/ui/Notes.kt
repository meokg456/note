package com.meokg456.note.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meokg456.note.R
import com.meokg456.note.databinding.FragmentNotesBinding
import com.meokg456.note.model.Note
import com.meokg456.note.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Notes : Fragment(R.layout.fragment_notes) {
    private val notesViewModel: NotesViewModel by activityViewModels()
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val resultNote = intent.getSerializableExtra(NOTE) as Note
                notesViewModel.updateNote(resultNote)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.notes.collect {
                    val noteAdapter = NoteAdapter(it.map { note ->
                        note.onTap = {
                            val intent = Intent(context, NoteDetail::class.java).apply {
                                putExtra(EDIT_MODE, true)
                                putExtra(NOTE, note)
                            }
                            startForResult.launch(intent)
                        }
                        note
                    })
                    binding.noteList.adapter = noteAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}