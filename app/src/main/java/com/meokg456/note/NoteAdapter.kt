package com.meokg456.note

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meokg456.note.uistate.NoteUiState
import java.text.SimpleDateFormat

class NoteAdapter(private val data: List<NoteUiState>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val titleTextView = view.findViewById<TextView>(R.id.title)
        private val timeTextView = view.findViewById<TextView>(R.id.time)
        private val shareImageButton = view.findViewById<ImageButton>(R.id.share_button)
        private var currentNoteUiState : NoteUiState? = null
        private val recourses = view.resources
        init {
            shareImageButton.setOnClickListener(this)
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(noteUiState: NoteUiState) {
            titleTextView.text = noteUiState.title
            currentNoteUiState = noteUiState

            if(noteUiState.modifiedAt == null) {
                timeTextView.text =  recourses.getString(R.string.created_at, noteUiState.createAt)
            }
            else {
                timeTextView.text =  recourses.getString(R.string.modified_at, noteUiState.modifiedAt)
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = data[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}