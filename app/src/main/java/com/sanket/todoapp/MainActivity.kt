package com.sanket.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD_NOTE = 1001
        const val REQUEST_EDIT_NOTE = 1002
    }

    private val notesViewModel by unsafeLazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }
    private val adapter by unsafeLazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.actionDeleteAllNotes -> {
                notesViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
        return Triple(title, description, priority)
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        fabAddNote.setOnClickListener { startActivityForResult(AddEditNoteActivity.newIntent(this), REQUEST_ADD_NOTE) }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvNotes.layoutManager = LinearLayoutManager(this)
        adapter.setCallback(object : NotesAdapter.Callback {
            override fun onNoteClick(note: Note) {
                startActivityForResult(AddEditNoteActivity.newIntent(this@MainActivity, note), REQUEST_EDIT_NOTE)
            }
        })
        rvNotes.adapter = adapter

        initSwipeToDismiss()
    }

    private fun initSwipeToDismiss() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(rvNotes)
    }

    private fun initViewModel() {
        notesViewModel.allNotes.observe(this, Observer {
            adapter.setNotes(it)
        })
    }
}
