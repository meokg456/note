package com.meokg456.note

import com.meokg456.note.model.Note
import com.meokg456.note.repository.NoteRepository
import com.meokg456.note.usecase.AddNoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

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