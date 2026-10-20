package com.canoezequiel7k.curso.notes.domain

import com.canoezequiel7k.curso.notes.data.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun addNote(title: String, content: String, important: Boolean)
    suspend fun deleteNote(note: Note)
    suspend fun clear()
}

