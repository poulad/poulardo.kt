fun main(args: Array<String>) {
    log("program started")
    println("Hello \n  World \t!".replaceMultipleWhitespaceEx())
    log(logLevel = 3, message = "another log at level 3")
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

fun log(message: String, logLevel: Int = 2): Boolean {
    println("LOG [$logLevel] >> $message")
    return true
}

/**
 * Replaces one or more whitespace characters in the text with a single space character
 */
fun String.replaceMultipleWhitespaceEx(): String = Regex("\\s+").replace(this, " ")