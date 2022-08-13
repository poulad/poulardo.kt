package funs

import java.math.BigDecimal
import java.math.BigInteger

/////////////////////////////////////////////////////////////

class TextBox(val Text: String?) {
    override fun toString(): String = "TextBox[Text=$Text]"
}

infix operator fun TextBox.plus(other: TextBox): TextBox {
    return TextBox("${this.Text} ${other.Text}")
}

/////////////////////////////////////////////////////////////

fun fib(n: Int): BigInteger =
    tailRecFib(BigInteger(n.toString()), BigInteger("1"), BigInteger("0"))


private tailrec fun tailRecFib(n: BigInteger, a: BigInteger, b: BigInteger): BigInteger =
    if (n == BigInteger("0")) b else tailRecFib(n - BigInteger("1"), a + b, a)

/////////////////////////////////////////////////////////////

fun countUpTo(n: Int, func: (Int) -> Unit) {
    for (i in 1..n) {
        func(i)
    }
}
