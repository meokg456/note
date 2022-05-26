package com.meokg456.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.meokg456.note.model.Note
import com.meokg456.note.repository.INoteRepository
import com.meokg456.note.repository.NoteRepository
import com.meokg456.note.ui.MainApplication
import com.meokg456.note.usecase.AddNoteUseCase
import com.meokg456.note.viewmodel.NoteDetailViewModel
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class AddNoteUseCaseTest {
    @MockK
    lateinit var noteRepository: NoteRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addNoteTest() = runTest {
        val now = Date()
        every {
            noteRepository.addNote(Note(createAt = now))
        } returns 10
        val dispatcher = StandardTestDispatcher(testScheduler)
        val addNoteUseCase = AddNoteUseCase(noteRepository, dispatcher)
        assertEquals(10, addNoteUseCase(Note(createAt = now)))
    }
}