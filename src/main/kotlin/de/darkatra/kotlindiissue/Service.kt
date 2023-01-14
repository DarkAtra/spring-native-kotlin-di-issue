package de.darkatra.kotlindiissue

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class Service(
    private val applicationContext: ApplicationContext,
    private val client: Client = Client()
) {

    fun getName(): String {
        return "World"
    }
}
