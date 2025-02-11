package example

import io.micronaut.context.annotation.Bean
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.json
import org.http4k.routing.bind
import org.http4k.routing.routes

@Controller("/")
class MicronautController(private val repository: UserRepository) {

    @Get("/users")
    fun hello(): List<User> = repository.finaAll()
}

@Bean
class Http4kController(private val repository: UserRepository) : HttpHandler by http4kApp(repository)

fun http4kApp(repository: UserRepository): HttpHandler =
    routes("/users_v2" bind Method.GET to { Response(OK).json(repository.finaAll()) })
