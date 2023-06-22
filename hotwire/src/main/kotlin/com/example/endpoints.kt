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
import org.http4k.sse.SseResponse
import org.http4k.template.TemplateRenderer
import java.lang.Thread.sleep
import java.time.LocalDateTime.now
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.MILLISECONDS
import org.http4k.routing.sse.bind as sseBind

fun index(renderers: SelectingViewModelRenderers) =
    "/" bind GET to { Response(OK).with(renderers(it) of Index) }

fun staticContent(hotReload: Boolean) =
    static(if (hotReload) Directory("./src/main/resources") else Classpath("public"))

fun clicks(renderers: SelectingViewModelRenderers) = "/clicks" bind POST to {
    Response(OK).with(renderers(it) of Clicks(now()))
}

fun hello(renderers: SelectingViewModelRenderers) = "/hello" bind GET to {
    if (Query.boolean().defaulted("sleep", false)(it)) sleep(1000)
    Response(OK).with(renderers.htmlViews of HelloWorld(Query.defaulted("person", "world")(it)))
}

fun time(renderer: TemplateRenderer): RoutingSseHandler {
    val executor = newSingleThreadScheduledExecutor()

    return "/time" sseBind {
        SseResponse { sse: Sse ->
            executor.scheduleWithFixedDelay({
                sse.send(SseMessage.Data(renderer(Time(now()))))
            }, 0, 1000, MILLISECONDS)
        }
    }
}
