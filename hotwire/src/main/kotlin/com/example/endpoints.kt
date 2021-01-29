package com.example

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.boolean
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.static
import org.http4k.websocket.Websocket
import java.time.LocalDateTime

fun index(lenses: SelectingViewModelLenses) =
    "/" bind Method.GET to { Response(Status.OK).with(lenses(it) of Index) }

fun staticContent(hotReload: Boolean) =
    static(if (hotReload) ResourceLoader.Directory("./src/main/resources") else ResourceLoader.Classpath("public"))

fun clicks(lenses: SelectingViewModelLenses) = "/clicks" bind Method.POST to {
    Response(Status.OK).with(lenses(it) of Clicks(LocalDateTime.now()))
}

fun hello(lenses: SelectingViewModelLenses) = "/hello" bind Method.GET to {
    if (Query.boolean().defaulted("sleep", false)(it)) Thread.sleep(1000)
    Response(Status.OK).with(lenses.htmlViews of HelloWorld(Query.defaulted("person", "world")(it)))
}

fun time(lenses: SelectingViewModelLenses) = "/time" bind { ws: Websocket ->
    while (true) {
        ws.send(lenses.websocketViews.create(Time(LocalDateTime.now())))
        Thread.sleep(1000)
    }
}
