package com.sanket.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val notesViewModel by unsafeLazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }
    private val adapter by unsafeLazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = adapter
    }

    private fun initViewModel() {
        notesViewModel.allNotes.observe(this, Observer {
            adapter.setNotes(it)
        })
    }
}
