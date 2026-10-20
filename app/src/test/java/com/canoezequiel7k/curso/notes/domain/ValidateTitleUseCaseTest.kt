package com.canoezequiel7k.curso.notes.domain

import org.junit.Assert.*
import org.junit.Test

class ValidateTitleUseCaseTest {
    //Creamos una instancia del caso de uso
    private val validateTitle = ValidateTitleUseCase()
    /**
        Nomenglatura utilizada
         GIVEN - WHEN - THEN
    */


    @Test
    //dado un titulo normal, cuando valides, entonces me retornaras true
    fun `given a normal title when validating then retorn true`(){
        //Given
        val title = "testOne"
        //When
        val result: Boolean = validateTitle(title)
        //Then
        assertTrue(result)
    }

    @Test
    //Dado que el titulo esta vacio, cuando valides, entonces es invalido
    fun `given an empty title when validating then it is invalid`(){
        //Given
        val title = ""
        //When
        val result: Boolean = validateTitle(title)
        //Then
        assertFalse(result)
    }
}