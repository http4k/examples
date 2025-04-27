package com.example

import org.http4k.core.PolyHandler
import org.http4k.core.then
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.routing.poly
import org.http4k.routing.routes
import org.http4k.routing.sse
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun Hotwire(hotReload: Boolean): PolyHandler {
    val renderers = when {
        hotReload -> SelectingViewModelRenderers { HotReload("./src/main/resources") }
        else -> SelectingViewModelRenderers { CachingClasspath() }
    }

    return poly(
        CatchAll().then(routes(staticContent(hotReload), hello(renderers), clicks(renderers), index(renderers))),
        sse(time(renderers.turboRenderer))
    )
}

fun main() {
    // if setting this to true, remember to run the app with the working directory set to the root of the example
    val hotReload = false

    Hotwire(hotReload).asServer(Undertow(8080)).start()
}
