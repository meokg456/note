package com.meokg456.note

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meokg456.note.databinding.NoteLayoutBinding
import com.meokg456.note.model.Note

class NoteAdapter(private val data: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(private val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        private var currentNote : Note? = null

        init {
            binding.shareButton.setOnClickListener { onShareClick(it) }
        }

        fun bind(note: Note) {
            binding.title.text = note.title
            currentNote = note

            if(note.modifiedAt == null) {
                binding.time.text =  itemView.context.getString(R.string.created_at, note.createAt)
            }
            else {
                binding.time.text =  itemView.context.getString(R.string.modified_at, note.modifiedAt)
            }

            itemView.setOnClickListener{
                currentNote?.onTap?.invoke()
            }

        }

        private fun onShareClick(view: View) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, currentNote?.title)
                putExtra(Intent.EXTRA_TEXT, currentNote?.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share your note")
            view.context?.startActivity(shareIntent)
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