package io.perfume.perfumebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PerfumeBackendApplication

fun main(args: Array<String>) {
    runApplication<PerfumeBackendApplication>(*args)
}
