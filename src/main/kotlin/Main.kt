import clazzes.Meeting
import clazzes.Student
import funs.TextBox
import funs.countUpTo
import funs.fib
import funs.plus
import generics.demoGenerics
import nullz.demoNull
import objekts.GeoLocation
import objekts.Objekt

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            log("program started")
            println("Hello \n  World \t!".replaceMultipleWhitespaceEx())
            log(logLevel = 3, message = "another log at level 3")
            // Try adding program arguments via Run/Debug configuration.
            // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
            println("Program arguments: ${args.joinToString()}")

            val tb = TextBox("tb1") + TextBox("txtbx2")
            println("Combined is: $tb")

            println("Fibonacci number is: ${fib(10_000)}")

            val student = Student("Joe", "Doe", 123)
            println(student.getName())

            val meetingNewYork = Meeting(546, "NYC", 111111111111)
            val meetingToronto = meetingNewYork.copy(location = "Toronto")
            println("Meeting in NYC ${meetingNewYork.hashCode()} $meetingNewYork")
            println("Meeting in Toronto ${meetingToronto.hashCode()} $meetingToronto")

            Objekt.MyNumbers.add(43)
            Objekt.printMe()

            println("GeoLocation is ${GeoLocation.of(34.123, -79.0054)}")

            var total = 0 // non-final var is mutated inside closure
            countUpTo(13) {
                total += it
                print("$it , ")
            }
            println("And their sum is $total")

            demoNull()

            val myArray = arrayOf("Foo", "Bar", "Baz", "Qux")
            myArray.forEachIndexed { i, str ->
                println("myArray[$i] = $str")
            }

            demoGenerics()
        }
    }
}

fun log(message: String, logLevel: Int = 2): Boolean {
    println("LOG [$logLevel] >> $message")
    return true
}

/**
 * Replaces one or more whitespace characters in the text with a single space character
 */
fun String.replaceMultipleWhitespaceEx(): String = Regex("\\s+").replace(this, " ")
