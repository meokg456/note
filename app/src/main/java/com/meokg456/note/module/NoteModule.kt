package com.meokg456.note.module

import com.meokg456.note.repository.INoteRepository
import com.meokg456.note.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
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