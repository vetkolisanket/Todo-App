package com.sanket.todoapp

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Sanket on 2019-07-12.
 */
@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    suspend fun getNote(noteId: Int): Note

}