package com.sanket.todoapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Sanket on 2019-07-12.
 */
class NotesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val noteDao: NoteDao

    init {
        val database = NoteDatabase.getInstance(context)
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
