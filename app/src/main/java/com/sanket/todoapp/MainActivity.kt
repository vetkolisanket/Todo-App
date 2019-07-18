package com.sanket.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private val notesViewModel by lazy { ViewModelProviders.of(this).get(NotesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesViewModel.allNotes.observe(this, Observer {
            Toast.makeText(this, "onChanged", Toast.LENGTH_SHORT).show()
        })
    }
}
