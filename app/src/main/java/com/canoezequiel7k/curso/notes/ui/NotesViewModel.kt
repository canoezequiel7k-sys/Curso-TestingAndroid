package com.canoezequiel7k.curso.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canoezequiel7k.curso.notes.data.Note
import com.canoezequiel7k.curso.notes.domain.GetNotesSummaryUseCase
import com.canoezequiel7k.curso.notes.domain.NotesRepository
import com.canoezequiel7k.curso.notes.domain.NotesSummary
import com.canoezequiel7k.curso.notes.domain.ValidateTitleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//UiState
data class NotesUiState(
    val title: String = "",
    val content: String = "",
    val important: Boolean = false,
    val importantOnly: Boolean = false,
    val notes: List<Note> = emptyList(),
    val notesSummary: NotesSummary = NotesSummary(0, 0, 0, 0),
    val canSave: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class NotesViewModel(
    private val repository: NotesRepository,
    //Casos de uso
    private val getNotesSummary: GetNotesSummaryUseCase = GetNotesSummaryUseCase(),
    private val validateTitle: ValidateTitleUseCase = ValidateTitleUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        NotesUiState(notesSummary = getNotesSummary(emptyList()))
    )
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private var allNotes: List<Note> = emptyList()

    init {
        observeNotes()
    }

    //Leemos el estado del repositorio
    private fun observeNotes() {
        viewModelScope.launch {
            //Los guardo y lo mando al repositorio.
            repository.getNotes().collect { notes ->
                allNotes = notes
                _uiState.update { current ->
                    current.copy(
                        notes = applyFilter(
                            notes = notes,
                            importantOnly = current.importantOnly
                        ),
                        notesSummary = getNotesSummary(notes)
                    )
                }
            }
        }
    }

    //Metodo para cambiar el title
    fun onTitleChanged(newTitle: String) {
        _uiState.update { current ->
            current.copy(
                title = newTitle,
                //Comprueba si puede guardar(Si esta habilitado o no).
                canSave = !current.isLoading && validateTitle(newTitle),
                errorMessage = null
            )
        }
    }

    //Metodo para cambiar el content
    fun onContentChanged(newContent: String) {
        _uiState.update { current ->
            current.copy(
                content = newContent,
                errorMessage = null
            )
        }
    }

    //Metodo para asignar si es importante o no
    fun onImportantChanged(newImportant: Boolean) {
        _uiState.update { current ->
            current.copy(
                important = newImportant,
                errorMessage = null
            )
        }
    }

    //Mostrar solo las task important
    fun onImportantOnlyToggled() {
        _uiState.update { current ->
            val newImportantOnly = !current.importantOnly
            current.copy(
                importantOnly = newImportantOnly,
                notes = applyFilter(
                    notes = allNotes,
                    importantOnly = newImportantOnly
                ),
                notesSummary = getNotesSummary(allNotes)
            )
        }
    }

    //Save task
    fun saveNote() {
        val current = _uiState.value
        val normalizedTitle = validateTitle.normalize(current.title)
        if (!validateTitle(current.title) || current.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, canSave = false, errorMessage = null) }
            try {
                repository.addNote(
                    title = normalizedTitle,
                    content = current.content,
                    important = current.important
                )
                _uiState.update { it.copy(title = "", content = "", important = false) }
            } catch (t: Throwable) {
                _uiState.update { it.copy(errorMessage = t.message) }
            } finally {
                _uiState.update { latest ->
                    latest.copy(
                        isLoading = false,
                        canSave = validateTitle(latest.title)
                    )
                }
            }
        }
    }

    //Elimino task
    fun onDeleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    private fun applyFilter(
        notes: List<Note>,
        importantOnly: Boolean
    ): List<Note> =
        if (importantOnly) notes.filter { it.important } else notes
}

