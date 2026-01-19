package com.takimaway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TakimawayApplication

fun main(args: Array<String>) {
	runApplication<TakimawayApplication>(*args)
}
