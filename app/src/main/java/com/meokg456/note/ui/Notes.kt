package com.meokg456.note.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.work.WorkManager
import com.meokg456.note.R
import com.meokg456.note.databinding.FragmentNotesBinding
import com.meokg456.note.datasource.NoteLocalDataSource.Companion.TOTAL_NOTE
import com.meokg456.note.viewmodel.NotesViewModel
import com.meokg456.note.workers.FetchNoteTotalWorker.Companion.Progress
import com.meokg456.note.workers.FetchNoteTotalWorker.Companion.Total
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Notes : Fragment(R.layout.fragment_notes) {
    private val notesViewModel: NotesViewModel by activityViewModels()
    private val pagingAdapter = NoteAdapter(NoteComparator)
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!


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
        binding.noteList.adapter = pagingAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.notes.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesViewModel.fetchNoteTotalPeriodically()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activity?.let {
                    WorkManager.getInstance(it.applicationContext)
                        // requestId is the WorkRequest id
                        .getWorkInfosForUniqueWorkLiveData(TOTAL_NOTE)
                        .observe(viewLifecycleOwner, { listWorker ->
                            for (workInfo in listWorker) {
                                Toast.makeText(
                                    it.applicationContext,
                                    workInfo.progress.getInt(Progress, 0).toString(),
                                    Toast.LENGTH_SHORT
                                ).show()

                                Toast.makeText(
                                    it.applicationContext,
                                    workInfo.outputData.getInt(Total, -1).toString(),
                                    Toast.LENGTH_SHORT
                                ).show()

                                Toast.makeText(
                                    it.applicationContext,
                                    workInfo.state.name,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}