package com.meokg456.note.module

import android.content.Context
import com.meokg456.note.database.AppDatabase
import com.meokg456.note.database.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) : NoteDao {
        return appDatabase.noteDao()
    }
}