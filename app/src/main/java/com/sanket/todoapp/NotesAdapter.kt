package com.sanket.todoapp

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by Sanket on 2019-07-18.
 */
class NotesAdapter() : ListAdapter<Note, NotesAdapter.NotesViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.priority == newItem.priority
            }

        }
    }

    private var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(parent.inflateView(R.layout.item_note))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback!!.onNoteClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(note: Note) {
            itemView.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                tvPriority.text = note.priority.toString()
                if (note.isCompleted) {
                    ivCheck.setImageResource(R.drawable.ic_check_box)
                    tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    ivCheck.setImageResource(R.drawable.ic_check_box_outline)
                    tvTitle.paintFlags = tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    interface Callback {
        fun onNoteClick(note: Note)
    }
}