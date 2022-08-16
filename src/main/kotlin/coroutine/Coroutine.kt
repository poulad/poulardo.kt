package coroutine

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.time.Duration

fun demoCoroutine() {
    GlobalScope.launch {
        delay(1_000)
        println("World from Kotlin Coroutine!")
    }

    print("Hello ")
    Thread.sleep(1_500)
}

fun main() = runBlocking {
    val fileSizeDeferred = async { downloadFileAsync() }
    println("Going to download a file(not really)")
    val fileSize = fileSizeDeferred.await()
    println("File size was $fileSize bytes")
}

suspend fun downloadFileAsync(): Long {
    println("File download started.")
    delay(Duration.parse("5s")) // downloading...
    println("File download is DONE!")
    return Random(System.currentTimeMillis()).nextLong()
}