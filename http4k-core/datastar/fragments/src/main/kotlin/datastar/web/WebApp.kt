package datastar.web

import datastar.Users
import org.http4k.core.then
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.routing.ResourceLoader
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.template.DatastarFragmentRenderer
import org.http4k.template.TemplateRenderer

fun WebApp(users: Users, renderer: TemplateRenderer): RoutingHttpHandler {
    val datastar = DatastarFragmentRenderer(renderer)
    return CatchAll().then(
        routes(
            static(ResourceLoader.Classpath("public")),
            manageUsers(datastar, users),
            pages(renderer),
        )
    )
}
