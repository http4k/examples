package datastar.sse

import datastar.Users
import dev.forkhandles.time.executors.SimpleScheduler
import org.http4k.filter.CatchAllSse
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingSseHandler
import org.http4k.routing.sse
import org.http4k.sse.then
import org.http4k.template.DatastarFragmentRenderer
import org.http4k.template.TemplateRenderer

fun SseApp(users: Users, renderer: TemplateRenderer, scheduler: SimpleScheduler): RoutingSseHandler {
    val datastarRenderer = DatastarFragmentRenderer(renderer)

    return ServerFilters.CatchAllSse()
        .then(
            sse(
                UserList(datastarRenderer, users, scheduler),
                BadApples(scheduler)
            )
        )
}
