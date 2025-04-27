package hotreload

import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.contentType
import org.http4k.routing.bind
import org.http4k.routing.routes

fun MyApp() = routes("/" bind GET to {
    // standard HTML response which will be reloaded when sources file are changed
    Response(OK)
        .contentType(TEXT_HTML)
        .body(EDIT_THIS_AND_SEE_THE_RELOAD_HAPPEN_IN_THE_BROWSER)
})

private val EDIT_THIS_AND_SEE_THE_RELOAD_HAPPEN_IN_THE_BROWSER = """
<body>
<h1>Hello, Hot-sssReload World!</h1>
<h2>Editasdsad me</h2>
</body>
"""
