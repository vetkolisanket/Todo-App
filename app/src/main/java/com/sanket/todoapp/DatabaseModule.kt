package com.sanket.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "note_database")
            .addMigrations(NoteDatabase.migration_1_2)
            .addCallback(NoteDatabase.roomCallback)
            .build()
    }

    @Provides
    fun provideNotesDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

}