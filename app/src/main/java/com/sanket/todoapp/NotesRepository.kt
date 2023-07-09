package com.sanket.todoapp

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

/**
 * Created by Sanket on 2019-07-12.
 */
class NotesRepository(application: Application) {

    private val noteDao: NoteDao
    val allNotes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAll() {
        DeleteAllNoteAsyncTask(noteDao).execute()
    }

    fun getNote(noteId: Int): Note {
        return noteDao.getNote(noteId)
    }

    companion object {
        class InsertNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.insert(notes[0])
                return null
            }
        }

        class UpdateNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.update(notes[0])
                return null
            }
        }

        class DeleteNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg notes: Note): Void? {
                noteDao.delete(notes[0])
                return null
            }
        }

        class DeleteAllNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Void, Void, Void>() {

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg voids: Void): Void? {
                noteDao.deleteAll()
                return null
            }
        }
    }


}
