package com.sanket.todoapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by Sanket on 2019-07-18.
 */
class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes by unsafeLazy { mutableListOf<Note>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(parent.inflateView(R.layout.item_note))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun setNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.tvTitle.text = note.title
            itemView.tvDescription.text = note.description
            itemView.tvPriority.text = note.priority.toString()
        }
    }
}