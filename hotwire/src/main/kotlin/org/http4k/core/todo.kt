package org.http4k.core

import org.http4k.lens.Header
import org.http4k.lens.string
import org.http4k.template.TemplateRenderer
import org.http4k.template.ViewModel
import org.http4k.websocket.WsMessage

// TODO  import from new http4k version
data class Accept(val contentTypes: List<ContentType>, val directives: Parameters) {
    /**
     * Note that the Accept header ignores CharSet because that is in a separate Accept-CharSet header..
     */
    fun accepts(contentType: ContentType): Boolean = contentTypes.any { it.equalsIgnoringDirectives(contentType)}
}

// TODO  import from new http4k version
fun WsMessage.Companion.viewModel(renderer: TemplateRenderer) =
    string().map<ViewModel>({ throw UnsupportedOperationException("Cannot parse a ViewModel") }, renderer::invoke)

// TODO  import from new http4k version
val Header.ACCEPT get() = Header.map {
    parseValueAndDirectives(it).let {
        Accept(it.first.split(",").map { it.trim() }.map(::ContentType), it.second)
    }
}.optional("Accept")

// REMOVE
internal fun parseValueAndDirectives(it: String): Pair<String, Parameters> =
    with(it.split(";").mapNotNull { it.trim().takeIf(String::isNotEmpty) }) {
        first() to drop(1).map {
            with(it.split("=")) {
                first() to if (size == 1) null else drop(1).joinToString("=")
            }
        }
    }
