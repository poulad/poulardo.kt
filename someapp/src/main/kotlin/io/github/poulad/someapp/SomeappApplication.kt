package io.github.poulad.someapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SomeappApplication

fun main(args: Array<String>) {
	runApplication<SomeappApplication>(*args)
}
