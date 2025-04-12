package datastar

import datastar.web.UserList
import datastar.web.index
import datastar.web.manageUsers
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.PolyHandler
import org.http4k.routing.ResourceLoader
import org.http4k.routing.poly
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.template.DatastarFragmentRenderer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.viewModel

fun UserManagement(): PolyHandler {
    val renderer = HandlebarsTemplates().CachingClasspath()
    val users = Users()

    val datastarRenderer = DatastarFragmentRenderer(renderer)

    return poly(
        UserList(datastarRenderer, users),
        routes(
            static(ResourceLoader.Classpath("public")),
            manageUsers(datastarRenderer, users),
            index(Body.viewModel(renderer, ContentType.TEXT_HTML).toLens()),
        )
    )
}

