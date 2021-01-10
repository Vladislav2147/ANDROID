package by.bstu.vs.stpms.lr13

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("Kotlin Start")
        GlobalScope.launch {
            delay(2000)
            println("Kotlin Inside")
        }
        println("Kotlin End")
        runBlocking {
            delay(2000)
        }
    }
}