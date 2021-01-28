package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.lens.Query
import org.http4k.lens.boolean
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.ResourceLoader.Companion.Directory
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import kotlin.random.Random

fun Hotwire(hotReload: Boolean): HttpHandler {
    val loaders = buildResourceLoaders(hotReload)

    return CatchAll()
        .then(
            routes(
                static(loaders),
                "/greeting" bind GET to {
                    if (Query.boolean().defaulted("sleep", false)(it)) Thread.sleep(1000)
                    Response(OK).with(loaders.rendererFor(it) of Greeting(Query.defaulted("person", "world")(it)))
                },
                "/pinger" bind POST to {
                    Response(OK).with(loaders.rendererFor(it) of Ping(Random.nextInt()))
                },
                "/" bind GET to { Response(OK).with(loaders.rendererFor(it) of Index) }
            )
        )
}

private fun buildResourceLoaders(hotReload: Boolean): TurboAwareResourceLoader =
    when {
        hotReload -> TurboAwareResourceLoader(
            Directory("./src/main/resources/public"),
            { HotReload("./src/main/resources") })
        else -> TurboAwareResourceLoader(Classpath("public"), { CachingClasspath() })
    }

fun main() {
    // if setting this to true, remember to run the app with the working directory set to the root of the example
    val hotReload = false

    Hotwire(hotReload).asServer(SunHttp(8080)).start()
}
