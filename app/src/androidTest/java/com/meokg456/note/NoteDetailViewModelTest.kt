package com.meokg456.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.meokg456.note.database.AppDatabase
import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import com.meokg456.note.module.DatabaseModule
import com.meokg456.note.repository.NoteRepository
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.viewmodel.NoteDetailViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.RuleChain
import java.lang.Thread.sleep
import java.util.*
import javax.inject.Inject

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
class NoteDetailViewModelTest {
    @Inject
    lateinit var noteDao: NoteDao
    private lateinit var viewModel: NoteDetailViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddNote() = runTest {
        val now = Date()
        val dispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NoteDetailViewModel(AddNoteUseCase(noteRepository, dispatcher))
        viewModel.note = Note(createAt = now)
        viewModel.save()
        advanceUntilIdle()
        val result =  noteDao.getNotes(10,0)

        assertEquals(listOf(viewModel.note), result)
    }
}