package com.example

import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.Request
import org.http4k.routing.ResourceLoader
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer
import org.http4k.template.viewModel

class TurboAwareResourceLoader(
    resources: ResourceLoader,
    rendererFn: HandlebarsTemplates.() -> TemplateRenderer
) : ResourceLoader by resources {

    fun rendererFor(request: Request) =
        when {
            request.header("Accept")?.contains(turboContentType.value) == true -> turbo
            else -> html
        }

    val html = Body.viewModel(
        rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".html" } }), ContentType.TEXT_HTML
    ).toLens()

    val turbo = Body.viewModel(
        rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".turbo-stream.html" } }), turboContentType
    ).toLens()

    companion object {
        val turboContentType = ContentType("text/vnd.turbo-stream.html")
    }
}
