package com.example

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
import org.http4k.routing.websockets
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.http4k.websocket.PolyHandler
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsHandler
import org.http4k.websocket.WsMessage
import kotlin.random.Random

fun Hotwire(hotReload: Boolean): PolyHandler {
    val loaders = buildResourceLoaders(hotReload)

    val app = CatchAll()
        .then(
            routes(
                static(loaders),
                "/greeting" bind GET to {
                    if (Query.boolean().defaulted("sleep", false)(it)) Thread.sleep(1000)
                    Response(OK).with(loaders.html of Greeting(Query.defaulted("person", "world")(it)))
                },
                "/pinger" bind POST to {
                    Response(OK).with(loaders.rendererFor(it) of Ping(Random.nextInt()))
                },
                "/" bind GET to { Response(OK).with(loaders.rendererFor(it) of Index) }
            )
        )


    val ws: WsHandler = websockets(
        "/load" bind { ws: Websocket ->
            repeat(Int.MAX_VALUE) {
                ws.send(
                    WsMessage(
                        Response(OK).with(loaders.turbo of Load(Random.nextInt(), Random.nextInt())).bodyString()
                    )
                )
                Thread.sleep(2000)
            }
        }
    )

    return PolyHandler(app, ws)
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

    Hotwire(hotReload).asServer(Netty(8080)).start()
}
