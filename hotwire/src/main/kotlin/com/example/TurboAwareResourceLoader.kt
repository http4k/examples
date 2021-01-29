package com.example

import org.http4k.core.ACCEPT
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Request
import org.http4k.core.viewModel
import org.http4k.lens.Header
import org.http4k.routing.ResourceLoader
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer
import org.http4k.template.viewModel
import org.http4k.websocket.WsMessage

class TurboAwareResourceLoader(
    resources: ResourceLoader,
    rendererFn: HandlebarsTemplates.() -> TemplateRenderer
) : ResourceLoader by resources {

    fun rendererFor(request: Request) =
        when {
            Header.ACCEPT(request)?.accepts(turboContentType) == true -> turbo
            else -> html
        }

    private val htmlRenderer = rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".html" } })
    val html = Body.viewModel(htmlRenderer, TEXT_HTML).toLens()

    private val turboRenderer = rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".turbo-stream.html" } })
    val turbo = Body.viewModel(turboRenderer, turboContentType).toLens()

    val turboLens = WsMessage.viewModel(turboRenderer).toLens()

    companion object {
        val turboContentType = ContentType("text/vnd.turbo-stream.html")
    }
}
