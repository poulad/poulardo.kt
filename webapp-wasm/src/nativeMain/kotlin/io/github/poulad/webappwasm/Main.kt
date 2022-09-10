fun main(): Unit {
    println("Hello, World from WASM!")
    alert("<Alert> Hello, World from WASM!")
}

external fun alert(message: Any?): Unit

//external class Node {
//    val firstChild: Node
//
//    fun append(child: Node): Node
//
//    fun removeChild(child: Node): Node
//
//    // etc
//}
//
//external class Window {
//
//}
//
//external val window: Window
