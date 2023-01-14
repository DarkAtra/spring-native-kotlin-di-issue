package de.darkatra.kotlindiissue

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<KotlinDiIssueApplication>(*args)
}

@SpringBootApplication
class KotlinDiIssueApplication(
    private val service: Service
) : ApplicationRunner {

    companion object {
        private val logger = LoggerFactory.getLogger(KotlinDiIssueApplication::class.java)
    }

    override fun run(args: ApplicationArguments?) {

        val name = service.getName()

        logger.info("Hello ${name}!")
    }
}
