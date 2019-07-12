package com.sanket.todoapp

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Sanket on 2019-07-12.
 */
@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

}