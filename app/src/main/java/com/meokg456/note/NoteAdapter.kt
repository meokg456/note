package com.meokg456.note

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.meokg456.note.core.Note
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NoteAdapter(private val data: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val titleTextView = view.findViewById<TextView>(R.id.title)
        private val timeTextView = view.findViewById<TextView>(R.id.time)
        private val shareImageButton = view.findViewById<ImageButton>(R.id.share_button)
        private var currentNote : Note? = null
        private val recourses = view.resources
        init {
            // Define click listener for the ViewHolder's View.
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(note: Note) {
            titleTextView.text = note.title
            currentNote = note
            shareImageButton.setOnClickListener(this)
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
            if(note.modifiedAt == null) {
                timeTextView.text =  recourses.getString(R.string.created_at, formatter.format(note.createAt))
            }
            else {
                timeTextView.text =  recourses.getString(R.string.modified_at, formatter.format(note.modifiedAt))
            }

        }

        override fun onClick(view: View?) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, currentNote?.title)
                putExtra(Intent.EXTRA_TEXT, currentNote?.content)
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