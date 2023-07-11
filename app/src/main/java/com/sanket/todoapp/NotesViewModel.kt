package com.sanket.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Sanket on 2019-07-18.
 */
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository: NotesRepository by lazy { NotesRepository(application) }
    val allNotes: LiveData<List<Note>> = notesRepository.getAllNotes()

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteAll()
        }
    }

    fun onCheckClick(noteId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = notesRepository.getNote(noteId)
            note.isCompleted = note.isCompleted.not()
            update(note)
        }

    }

}