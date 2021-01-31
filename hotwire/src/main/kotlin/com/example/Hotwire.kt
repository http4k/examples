package com.example

import org.http4k.core.then
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.routing.routes
import org.http4k.routing.sse
import org.http4k.server.PolyHandler
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun Hotwire(hotReload: Boolean): PolyHandler {
    val lenses = when {
        hotReload -> SelectingViewModelLenses { HotReload("./src/main/resources") }
        else -> SelectingViewModelLenses { CachingClasspath() }
    }

    return PolyHandler(
        CatchAll().then(routes(staticContent(hotReload), hello(lenses), clicks(lenses), index(lenses))),
        sse = sse(time(lenses))
    )
}

fun main() {
    // if setting this to true, remember to run the app with the working directory set to the root of the example
    val hotReload = false

    Hotwire(hotReload).asServer(Undertow(8080)).start()
}
