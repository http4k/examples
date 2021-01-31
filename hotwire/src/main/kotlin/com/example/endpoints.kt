package com.example

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.boolean
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.ResourceLoader.Companion.Directory
import org.http4k.routing.RoutingSseHandler
import org.http4k.routing.bind
import org.http4k.routing.static
import org.http4k.sse.Sse
import org.http4k.sse.SseMessage
import java.time.LocalDateTime.now
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun index(lenses: SelectingViewModelLenses) =
    "/" bind GET to { Response(OK).with(lenses(it) of Index) }

fun staticContent(hotReload: Boolean) =
    static(if (hotReload) Directory("./src/main/resources") else Classpath("public"))

fun clicks(lenses: SelectingViewModelLenses) = "/clicks" bind POST to {
    Response(OK).with(lenses(it) of Clicks(now()))
}

fun hello(lenses: SelectingViewModelLenses) = "/hello" bind GET to {
    if (Query.boolean().defaulted("sleep", false)(it)) Thread.sleep(1000)
    Response(OK).with(lenses.htmlViews of HelloWorld(Query.defaulted("person", "world")(it)))
}

fun time(lenses: SelectingViewModelLenses): RoutingSseHandler {
    val executor = Executors.newSingleThreadScheduledExecutor()

    return "/time" bind { sse: Sse ->
        executor.scheduleWithFixedDelay({
            sse.send(SseMessage.Data(lenses.turboRenderer(Time(now()))))
        }, 0, 1000, TimeUnit.MILLISECONDS)
    }
}
