package com.canoezequiel7k.curso.notes.domain

import com.canoezequiel7k.curso.notes.data.Note
import org.junit.Assert.*
import org.junit.Test

class GetNotesSummaryUseCaseTest {
    //Creamos una instancia del caso de uso GetNotesSummaryUseCase
    private val getNoteSummary = GetNotesSummaryUseCase()

    //Esta funcion lo podemos poner tanto en el principio como en el final.
    private fun noteWith(
        important: Int,
        normal: Int
        //El buildList, te permite crear listados de golpe
    ):List<Note> = buildList{
        repeat(important){add(Note(title = "Important$it", content = "", important = true))}
        repeat(normal){add(Note(title = "Normal$it", content = "", important = false))}
    }
    @Test
    //dado que las notas solo son importantes, cuando calculemos el sumario, entonces el porcentaje deberia ser 100
    fun `given only important notes when calculating the summary then the porcentage is 100`(){
//        //given PARA NO ESCRIBIR ESTO 100 VECES A MANO
//        val notes = listOf<Note>(
//            //Recibe un Dato Note, de /data.Note.kt con sus atributos necesarios
//            Note(title = "Note 1", content = "Body", important = true),
//            Note(title = "Note 2", content = "Body", important = true)
//        )
        //Le pasamos a noteWith() el valor de la cantidad de notas
        val notes = noteWith(important = 1000, normal = 0)
        //when
        val result = getNoteSummary(notes)
        //then
        assertEquals(100, result.importantPercentage) //Comprobador de equivalencia(Lo que espera y donde comprobar ese valor)

    }

    @Test
    //Dado que no hay notas, cuando calcules el summary, entonces su porcentaje es 0
    fun `given no notes when calculating the summary then the porcentage is 0`(){
        //given
        val notes = emptyList<Note>()
        //when
        val result = getNoteSummary(notes)
        //then
        assertEquals(0, result.importantPercentage)
    }

    /**ESTO ES UN TEST ESPECIFICO*/
    @Test
    //dado un elemento importante de un total de 6, cuando calcules el summary, entonces se redondea a 17
    fun `given 1 important out of 6 when calculating summary then it round to 17`(){
        //given
        val notes = noteWith(
            important = 1,
            normal = 5
        )
        //when
        val result = getNoteSummary(notes)
        //then
        assertEquals(17, result.importantPercentage)
    }

    @Test
    //dado a una cambinacion de notas, cuando calcules el summary, entonces retorna todos los totales
    fun `given a mix of notes when calculate the summary then it return all the totals`(){
        //Given
        val notes = noteWith(important = 2, normal = 1)
        //When
        val result = getNoteSummary(notes)
        //Then
        //Que es 3 el total
        assertEquals(3, result.total)
        //Que son 2 tareas importantes
        assertEquals(2, result.important)
        //Que es una tarea importante
        assertEquals(1, result.nonImportant)
        //Que es 67% el porcentaje de importantes
        assertEquals(67, result.importantPercentage)
    }

    @Test
    //Dado que tebenis 0 notas importantes, cuando calculemos el summary, entonces el porcentaje es 0
    fun `given 0 notes important when calculating the summary then the porcentage is 0`(){
        //Given
        val notes = noteWith(important = 0, normal = 5)
        //When
        val result = getNoteSummary(notes)
        //Then
        assertEquals(0, result.importantPercentage)
    }

    @Test
    //dado que tenemos un mix de task importantes y no importantes, cuando calculemos el summary, entonces el porcentaje es 50%
    fun `given a mix notes important and nonimportant when calculating the summary then the porcentage is 50`(){
        //given
        val notes = noteWith(important = 5, normal = 5)
        //when
        val result = getNoteSummary(notes)
        //then
        assertEquals(50, result.importantPercentage)
    }

    @Test
    //dado que no hay notasm cuando calculemos el summary, entonces el total de todo es 0
    fun `given empty notes when calculating the summary then all total is 0`(){
        //given
        val notes = emptyList<Note>()
        //when
        val result = getNoteSummary(notes)
        //then
        //El total de notas es 0 dado que la lista esta vacia
        assertEquals(0, result.total)
        //El total de notas importantes es 0 dado que la lista esta vacia
        assertEquals(0, result.important)
        //El total de notas no importantes es 0 dado que la lista esta vacia
        assertEquals(0, result.nonImportant)
        //El total del porcentaje de importantes es 0 dado que la lista esta vacia
        assertEquals(0, result.importantPercentage)
    }

    @Test
    //dado que tenemos el .roundToInt, teniendo 2 normales y 1 importante, cuando calculemos el summary entonces su porcentaje es 33
    fun `given notes normal 2 and important 1 when calculating the summary then porcentage is 33`(){
        //given
        val notes = noteWith(important = 1, normal = 2)
        //when
        val result = getNoteSummary(notes)
        //then
        assertEquals(33, result.importantPercentage)
    }
}