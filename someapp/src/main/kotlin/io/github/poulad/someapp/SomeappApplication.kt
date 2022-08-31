package io.github.poulad.someapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SomeAppApplication

fun main(args: Array<String>) {
	runApplication<SomeAppApplication>(*args)
}
