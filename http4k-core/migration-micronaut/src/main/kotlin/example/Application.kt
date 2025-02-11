package example

import io.micronaut.http.annotation.Controller
import io.micronaut.runtime.Micronaut.run
import org.http4k.bridge.MicronautToHttp4kFallbackController
import org.http4k.core.HttpHandler

fun main(args: Array<String>) {
    run(*args)
}

@Controller("/")
class FallbackController(override val http4k: HttpHandler) : MicronautToHttp4kFallbackController
