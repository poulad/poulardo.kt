package io.github.poulad.practice.generics

fun demoGenerics() {
    val listOfDoubles = listOf(Node(1.1), Node(1.2))
    println(listOfDoubles)

    val numbers = listOf(432, 654.1, 3.0f, 345_89594L, 111, 3)
    val integersOnly = numbers.isOfType<Int>()
    println("inlined func with reified type param ~~> $integersOnly")
}

data class Node<TValue : Number>(private val value: TValue) {
    fun fetchValue(): TValue = value
}

inline fun <reified T> List<Any>.isOfType(): List<T> {
    val matchingItems = mutableListOf<T>()
    for (i in this) {
        if (i is T) {
            matchingItems.add(i)
        }
    }
    return matchingItems
}
