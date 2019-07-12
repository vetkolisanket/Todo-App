package com.sanket.todoapp

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Sanket on 2019-07-12.
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: NoteDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance as NoteDatabase
        }

        val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance!!).execute()
            }
        }

        class PopulateDbAsyncTask(db: NoteDatabase) : AsyncTask<Void, Void, Void>() {

            private val noteDao by lazy { db.noteDao() }


            override fun doInBackground(vararg params: Void?): Void? {
                noteDao.insert(Note("Title 1", "Description 1", 1))
                noteDao.insert(Note("Title 2", "Description 2", 2))
                noteDao.insert(Note("Title 3", "Description 2", 3))
                return null
            }
        }

    }

}
