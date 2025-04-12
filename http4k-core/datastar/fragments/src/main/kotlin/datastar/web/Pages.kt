package datastar.web

import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.template.TemplateRenderer
import org.http4k.template.viewModel

fun pages(renderer: TemplateRenderer): RoutingHttpHandler {
    val viewLens = Body.viewModel(renderer, ContentType.TEXT_HTML).toLens()
    return routes(
        index(viewLens)
    )
}