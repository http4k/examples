package merge_fragments

import merge_fragments.web.UserList
import merge_fragments.web.index
import merge_fragments.web.manageUsers
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.PolyHandler
import org.http4k.routing.poly
import org.http4k.routing.routes
import org.http4k.template.DatastarElementRenderer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.viewModel

fun UserManagement(): PolyHandler {
    val renderer = HandlebarsTemplates().CachingClasspath()
    val users = Users()

    val datastarRenderer = DatastarElementRenderer(renderer)

    return poly(
        UserList(datastarRenderer, users),
        routes(
            manageUsers(datastarRenderer, users),
            index(Body.viewModel(renderer, ContentType.TEXT_HTML).toLens()),
        )
    )
}

