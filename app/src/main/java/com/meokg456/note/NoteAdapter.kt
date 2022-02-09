package com.meokg456.note

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meokg456.note.databinding.NoteLayoutBinding
import com.meokg456.note.uistate.NoteUiState
import java.text.SimpleDateFormat

class NoteAdapter(private val data: List<NoteUiState>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(private val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var currentNoteUiState : NoteUiState? = null
        init {
            binding.shareButton.setOnClickListener(this)
        }

        fun bind(noteUiState: NoteUiState) {
             binding.title.text = noteUiState.title
            currentNoteUiState = noteUiState

            if(noteUiState.modifiedAt == null) {
                binding.time.text =  itemView.context.getString(R.string.created_at, noteUiState.createAt)
            }
            else {
                binding.time.text =  itemView.context.getString(R.string.modified_at, noteUiState.modifiedAt)
            }

        }

        override fun onClick(view: View?) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, currentNoteUiState?.title)
                putExtra(Intent.EXTRA_TEXT, currentNoteUiState?.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share your note")
            view?.context?.startActivity(shareIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = data[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}