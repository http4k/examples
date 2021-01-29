package com.example

import org.http4k.core.ACCEPT
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Request
import org.http4k.core.viewModel
import org.http4k.lens.Header
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer
import org.http4k.template.viewModel
import org.http4k.websocket.WsMessage

class SelectingViewModelLenses(
    rendererFn: HandlebarsTemplates.() -> TemplateRenderer
) {
    private val htmlRenderer = rendererFn(HandlebarsTemplates { it
        .apply { loader.suffix = ".html" } })
    private val turboRenderer = rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".turbo-stream.html" } })

    val websocketViews = WsMessage.viewModel(turboRenderer).toLens()
    val htmlViews = Body.viewModel(htmlRenderer, TEXT_HTML).toLens()
    val turboViews = Body.viewModel(turboRenderer, ContentType.TURBO_STREAM).toLens()

    /**
     * Select the correct renderer for the request
     */
    operator fun invoke(request: Request) =
        when {
            Header.ACCEPT(request)?.accepts(ContentType.TURBO_STREAM) == true -> turboViews
            else -> htmlViews
        }
}

val ContentType.Companion.TURBO_STREAM get() = ContentType("text/vnd.turbo-stream.html")
