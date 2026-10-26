package com.canoezequiel7k.curso.notes.ui

import com.canoezequiel7k.curso.notes.MainDispatcherRule
import com.canoezequiel7k.curso.notes.data.Note
import com.canoezequiel7k.curso.notes.domain.NotesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class NotesViewModelTest {

    //Instancia del viewModel(Deberiamos hacerlo a dentro de cada test)
    //val viewmodel = ViewModel()

    //Para trabajar
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    //Mockeamos el repositorio; puedes pasar como parametro relaxed = true para que le de un valor generico a tus test
    private val repository: NotesRepository = mockk()

    @Test
    //dado un titulo invalido, cuando carges el titulo, entonces canSave es false
    fun `given an invalid title when changing the title then canSave is false`(){
        //Given
        //Siempre que sea una funcion normal, every
        every {
            //Cada vez que alguien llame al repositorio getNotes
            repository.getNotes()
        }returns flowOf(emptyList()) //Vas a retornar
        //Inicializamos el viewModel
        val viewModel = NotesViewModel(repository)

        //when
        viewModel.onTitleChanged("ab")

        //then
        assertFalse(viewModel.uiState.value.canSave)
    }


    @Test
    //Te doy una nota, cuando la borres, entonces deberia eliminarse del repositorio
    fun`given a note when deleting then it is removed from the repository`(){
        //Given
        every { repository.getNotes() } returns flowOf(emptyList())
        //Siempre que sea una funcion suspendida, coEvery
        coEvery {
            //Cuando llames a deleteNote y necibes este parametro, haz algo... Si te da igual, asigna any()
            repository.deleteNote(any())
        } just Runs //Haz lo que te quieras.
        val viewModel = NotesViewModel(repository)
        val note = Note(
            id = 7,
            title = "title",
            content = "",
            important = false
        )

        //When
        viewModel.onDeleteNote(note)

        //then
        coVerify {
            //Comprueba que se llamo al repository.delete y que ademas le pasaste note
            repository.deleteNote(note)
        }
    }
}