package com.canoezequiel7k.curso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.room.Room
import com.canoezequiel7k.curso.notes.data.NotesDatabase
import com.canoezequiel7k.curso.notes.data.RoomNotesRepository
import com.canoezequiel7k.curso.notes.ui.NotesScreen
import com.canoezequiel7k.curso.notes.ui.NotesViewModel
import com.canoezequiel7k.curso.ui.theme.CursoTestingAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //DB Room
        val db = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes-db"
        ).build()

        //Repositorio del viewModel
        val repository = RoomNotesRepository(db.noteDao())
        val viewModel = NotesViewModel(repository)

        setContent {
            CursoTestingAndroidTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //Inicializamos nuestra Screen
                    NotesScreen(viewModel = viewModel)
                }
            }
        }
    }
}