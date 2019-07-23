package com.sanket.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD_NOTE = 1001
    }

    private val notesViewModel by unsafeLazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }
    private val adapter by unsafeLazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_ADD_NOTE == requestCode && Activity.RESULT_OK == resultCode) {
            val title = data!!.getStringExtra(AddNoteActivity.EXTRA_TITLE)
            val description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION)
            val priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1)

            notesViewModel.insert(Note(title, description, priority))
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapter

        fabAddNote.setOnClickListener { startActivityForResult(AddNoteActivity.newIntent(this), REQUEST_ADD_NOTE) }
    }

    private fun initViewModel() {
        notesViewModel.allNotes.observe(this, Observer {
            adapter.setNotes(it)
        })
    }
}
