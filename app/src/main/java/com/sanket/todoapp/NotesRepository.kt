package com.sanket.todoapp

import android.app.Application
import androidx.lifecycle.LiveData

/**
 * Created by Sanket on 2019-07-12.
 */
class NotesRepository(application: Application) {

    private val noteDao: NoteDao

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    suspend fun getNote(noteId: Int): Note {
        return noteDao.getNote(noteId)
    }

}
