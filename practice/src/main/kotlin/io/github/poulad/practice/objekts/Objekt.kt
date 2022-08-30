package io.github.poulad.practice.objekts

import java.io.Closeable

object Objekt {
    const val Name = "singleton1"
    val MyNumbers = arrayListOf<Int>()

    fun printMe() {
        println("object $Name with numbers $MyNumbers")
    }
}

class GeoLocation private constructor(lat: Double, lon: Double) {
    private val latitude: Double = lat
    private val longitude: Double = lon

    override fun toString() = "( $latitude , $longitude )"

    companion object : Closeable {
        fun of(lat: Double, lon: Double): GeoLocation {
            // TODO: validate range of input params
            return GeoLocation(lat, lon)
        }

        override fun close() {
            println("closed!")
        }
    }
}
