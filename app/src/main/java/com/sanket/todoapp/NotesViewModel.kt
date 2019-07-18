package com.sanket.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/**
 * Created by Sanket on 2019-07-18.
 */
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository: NotesRepository by lazy { NotesRepository(application) }
    val allNotes: LiveData<List<Note>> by lazy { notesRepository.allNotes }

    fun insert(note: Note) {
        notesRepository.insert(note)
    }

    fun update(note: Note) {
        notesRepository.update(note)
    }

    fun delete(note: Note) {
        notesRepository.delete(note)
    }

    fun deleteAllNotes() {
        notesRepository.deleteAll()
    }

}