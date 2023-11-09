package bootiful.appwrite

import io.appwrite.Client
import io.appwrite.services.Locale
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.boot.ApplicationRunner as ApplicationRunner

@SpringBootApplication
class AppwriteApplication

fun main(args: Array<String>) {
    runApplication<AppwriteApplication>(*args)
}

// https://appwrite.io/docs/references/cloud/server-kotlin/databases

@ResponseBody
@Controller
class AppwriteRunner(
        @Value("\${appwrite.key}") private val key: String,
        @Value("\${appwrite.project-id}") private val projectId: String,
        @Value("\${appwrite.api-endpoint}") private val apiEndpoint: String) {


    suspend fun locales(client: Client) {
        val locale = Locale(client)

        locale.listCodes().toMap().forEach {
            println("got ${it.key}=${it.value}")
        }

    }

    @GetMapping("/locales")
    suspend fun locales() {

        val client = Client()
                .setEndpoint(apiEndpoint) // Your API Endpoint
                .setProject(projectId) // Your project ID
                .setKey(key) // Your secret API key

        locales(client)

    }


}
