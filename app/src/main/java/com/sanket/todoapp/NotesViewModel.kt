package com.sanket.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Sanket on 2019-07-18.
 */

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

    val allNotes: LiveData<List<Note>> = notesRepository.getAllNotes()

    fun insert(note: Note) {
        viewModelScope.launch {
            notesRepository.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            notesRepository.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            notesRepository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            notesRepository.deleteAll()
        }
    }

    fun onCheckClick(noteId: Int) {
        viewModelScope.launch {
            val note = notesRepository.getNote(noteId)
            note.isCompleted = note.isCompleted.not()
            update(note)
        }

    }

}