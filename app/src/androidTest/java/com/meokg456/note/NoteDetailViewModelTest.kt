package com.meokg456.note

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.meokg456.note.database.NoteDao
import com.meokg456.note.model.Note
import com.meokg456.note.module.BackgroundModule
import com.meokg456.note.module.DatabaseModule
import com.meokg456.note.repository.NoteRepository
import com.meokg456.note.ui.MainApplication
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.viewmodel.NoteDetailViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.RuleChain
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
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
        hiltRule.inject()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAddNote() = runTest {
        val now = Date()
        val dispatcher = StandardTestDispatcher(testScheduler)
        viewModel = NoteDetailViewModel(AddNoteUseCase(noteRepository, dispatcher), noteRepository)
        viewModel.note = Note(createAt = now)
        viewModel.save()
        advanceUntilIdle()
        val result =  noteDao.getNotes(10,0)

        assertEquals(listOf(viewModel.note), result)
    }
}