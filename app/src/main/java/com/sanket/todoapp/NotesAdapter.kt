package com.sanket.todoapp

import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanket.todoapp.databinding.ItemNoteBinding

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
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
                        && oldItem.isCompleted == newItem.isCompleted
            }

        }
    }

    private var callback: Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
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

    fun sortListAndSubmit(list: List<Note>) {
        submitList(list.sorted().reversed())
    }

    inner class NotesViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivCheck.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val note = getItem(adapterPosition)
                    callback?.onCheckClick(note.id)
                }
            }
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onNoteClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvDescription.text = note.description
                setIsCompleted(note.isCompleted)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.clNote.setBackgroundColor(ContextCompat.getColor(binding.cvNote.context, getCardBackground(note.getNotePriority())))
                }
            }
        }

        private fun getCardBackground(notePriority: Note.Priority): Int {
            return when (notePriority) {
                Note.Priority.HIGH -> R.color.holo_red_ten_percent_opacity
                Note.Priority.MEDIUM -> R.color.holo_orange_ten_percent_opacity
                Note.Priority.LOW -> R.color.holo_blue_ten_percent_opacity
            }
        }

        private fun setIsCompleted(isCompleted: Boolean) {
            binding.apply {
                if (isCompleted) {
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
        fun onCheckClick(noteId: Int)
    }
}