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

    /**================
    |   HAPPY PATHS   |
    ===================

    - PRUEBAN QUE TU CODIGO HACE LO QUE DEBE HACER CUANDO LE DAS LO CORRECTO

    */

    @Test
    //dado un titulo normal, cuando valides, entonces me retornaras true
    fun `given a normal title when validating then return true`(){
        //Given
        val title = "testOne"
        //When
        val result: Boolean = validateTitle(title)
        //Then
        assertTrue(result)
    }

    @Test
    //dado un titulo con espacios a los costados, cuando valides, entonces retornaras true
    fun `given a title with surrounding space when validating then return true`(){
        //given
        val title = " Prueba "
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertTrue(result)
    }

    @Test
    //dado un titulo con simbolos permitidos ('.', ',', '!', '?'), cuando valides, entonces retorna true
    fun `given a title with allowed symbols`(){
        //given
        val title = "Hola, como estás!?"
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertTrue(result)
    }


    /**================
    |    SAD PATHS    |
    ===================

    - PRUEBAN QUE TU CODIGO SE DEFIENDE BIEN Y RECHAZA LO INCORRECTO

    */

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

    @Test
    //Dado que el titulo es corto, cuando valides, entonces es invalido
    fun `given a to short title when validating then it is invalid`(){
        //given
        val title = "ab"
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertFalse(result)
    }

    @Test
    //Dado que el titulo es largo, cuando valides, entonces es invalido
    fun `given a to long title when validating then it is invalid`(){
        //given
        val title = "a".repeat(50) //El .repeat(50) esta diciendo que repita a 50 veces y se vaya concatenando.
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertFalse(result)
    }

    @Test
    //Dado a que el titulo tiene un caracter invalido, cuando valides, entonces es invalido
    fun `given a title with invalid characters when validating then it is invalid`(){
        //given
        val title = "ezequiel-sys@system.com"
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertFalse(result)
    }

    @Test
    //dado un titulo sin letras(solo numeros) cuando valides, entonces es invalido
    fun `given a title without letters when validating then it is invalid`(){
        //given
        val title = "12345678"
        //when
        val result: Boolean = validateTitle(title)
        //then
        assertFalse(result)
    }

    @Test
    //dado un titulo con espacios dobles consecutivos, cuando valides, entonces es invalido
    fun `given a title with spaces double when validating then it is invalid`(){
        //Given
        val title = "Hola  como estas?"
        //When
        val result: Boolean = validateTitle(title)
        //Then
        assertFalse(result)
    }


}