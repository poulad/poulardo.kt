package nullz

data class Foo(val bar: Int, val baz: String)

fun demoNull() {
    val foo100 = Foo(100, "F00")
    printFooNonNull(foo100)

    val fooNull: Foo? = null
    fooNull?.let(::printFooNonNull)
}

fun printFooNonNull(foo: Foo) {
    println("Foo<bar=${foo.bar},baz=${foo.baz}>")
}

