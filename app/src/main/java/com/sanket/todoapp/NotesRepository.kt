package com.sanket.todoapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Sanket on 2019-07-12.
 */
class NotesRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            noteDao.deleteAll()
        }
    }

    suspend fun getNote(noteId: Int): Note {
        return withContext(Dispatchers.IO) {
            noteDao.getNote(noteId)
        }
    }

}
