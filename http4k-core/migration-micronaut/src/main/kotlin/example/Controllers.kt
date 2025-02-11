package example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/")
class BackwardCompatibleController {

    @Get(uris = ["/users"]) // (1)
    fun hello(): String { // (2)
        return "Hello from Micronaut"
    }
}
