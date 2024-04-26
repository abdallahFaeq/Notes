package com.training.hilt_roomdatabase.di

import android.content.Context
import androidx.room.Room
import com.training.hilt_roomdatabase.db.NoteDatabase
import com.training.hilt_roomdatabase.db.NoteEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context:Context) = Room.databaseBuilder(context,NoteDatabase::class.java,"note_db")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db:NoteDatabase) = db.getNoteDao()

    @Provides
    fun provideNoteEntity() = NoteEntity()
}