package com.example

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.http4k.bridge.KtorToHttp4kApplicationPlugin
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(KtorToHttp4kApplicationPlugin { Response(OK).body("Hello from http4k") })

    routing {
        get("/") {
            call.respondText("Hello From Ktor!")
        }
    }
}
