package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun demoCoroutine() {
    GlobalScope.launch {
        delay(1_000)
        println("World from Kotlin Coroutine!")
    }

    print("Hello ")
    Thread.sleep(1_500)
}