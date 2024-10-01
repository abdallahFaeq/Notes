package com.training.hilt_roomdatabase.di

import android.content.Context
import androidx.room.Room
import com.training.hilt_roomdatabase.core_data.local.dao.NoteDao
import com.training.hilt_roomdatabase.core_data.local.db.NoteDatabase
import com.training.hilt_roomdatabase.core_data.local.entity.NoteEntity
import com.training.hilt_roomdatabase.core_data.repository.NoteRepositoryImp
import com.training.hilt_roomdatabase.core_domain.repository.NoteRepository
import com.training.hilt_roomdatabase.utils.Constants
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
    fun provideRoomDatabase(@ApplicationContext context:Context) = Room.databaseBuilder(context,
        NoteDatabase::class.java,Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase) = db.getNoteDao()

    @Provides
    fun provideNoteEntity() = NoteEntity()

    @Provides
    fun provideNoteRepositoryInterface(dao: NoteDao): NoteRepository {
        return NoteRepositoryImp(dao)
    }
}