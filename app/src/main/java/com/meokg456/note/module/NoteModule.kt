package com.meokg456.note.module

import com.meokg456.note.repository.INoteRepository
import com.meokg456.note.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NoteModule {
    @Binds
    @Singleton
    abstract fun bindINoteRepository(
        noteRepository: NoteRepository
    ): INoteRepository

}