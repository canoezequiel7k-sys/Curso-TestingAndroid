package com.canoezequiel7k.curso.notes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


//Sustituir el hilo principal
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()): TestRule {

    //Metodo que define como implementamos los hilos que vamos a utilizar(Aplica las reglas)
    override fun apply(
        //Recibe valores
        base: Statement,
        description: Description?
    ): Statement = object : Statement() {
        //Metodo que evalua
        override fun evaluate() {
            //Le esta diciendo que en el hilo principal, lo cambia por el testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
            Dispatchers.setMain(testDispatcher)
            //Y por seguridad
            try {
                //Por las dudas evaluate
                base.evaluate()
            }finally {
                //Agarramos los Dispatcher y los reseteamos.
                Dispatchers.resetMain()
            }
        }
    }
}