package com.sanket.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanket.todoapp.databinding.ActivityNotesListBinding

class NotesListActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD_NOTE = 1001
        const val REQUEST_EDIT_NOTE = 1002
    }

    private val notesViewModel by unsafeLazy {
        ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }
    private val adapter by unsafeLazy { NotesAdapter() }
    private val binding: ActivityNotesListBinding by lazy {
        ActivityNotesListBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionDeleteAllNotes -> {
                notesViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_ADD_NOTE == requestCode && Activity.RESULT_OK == resultCode) {
            val (title, description, priority) = getNoteData(data)

            notesViewModel.insert(Note(title, description, priority))
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else if (REQUEST_EDIT_NOTE == requestCode && Activity.RESULT_OK == resultCode) {

            val id = data!!.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Error while updating note!", Toast.LENGTH_SHORT).show()
            }

            val (title, description, priority) = getNoteData(data)

            val note = Note(title, description, priority)
            note.id = id
            notesViewModel.update(note)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNoteData(data: Intent?): Triple<String, String, Int> {
        val title = data!!.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
        val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
        val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
        return Triple(title!!, description!!, priority)
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        binding.fabAddNote.setOnClickListener {
            startActivityForResult(
                AddEditNoteActivity.newIntent(
                    this
                ), REQUEST_ADD_NOTE
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        adapter.setCallback(object : NotesAdapter.Callback {
            override fun onNoteClick(note: Note) {
                startActivityForResult(
                    AddEditNoteActivity.newIntent(this@NotesListActivity, note),
                    REQUEST_EDIT_NOTE
                )
            }

            override fun onCheckClick(noteId: Int) {
                notesViewModel.onCheckClick(noteId)
            }
        })
        binding.rvNotes.adapter = adapter

        initSwipeToDismiss()
    }

    private fun initSwipeToDismiss() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@NotesListActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.rvNotes)
    }

    private fun initViewModel() {
        notesViewModel.allNotes.observe(this) {
            adapter.sortListAndSubmit(it)
        }
    }
}
