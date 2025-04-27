package gameoflife.app

import gameoflife.model.AppState
import gameoflife.model.Cell
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.PolyHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.lens.Cookies
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.poly
import org.http4k.routing.sse
import org.http4k.routing.to
import org.http4k.sse.sendMergeFragments
import org.http4k.template.DatastarFragmentRenderer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer
import org.http4k.template.ViewModel
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread
import kotlin.random.Random


data class Index(val boardSize: Int) : ViewModel

data class GameBoardState(val cells: List<Cell>) : ViewModel

fun GameOfLife(boardSize: Int): PolyHandler {
    val state = AppState(boardSize)
    val sessions = ConcurrentHashMap<String, Int>()

    val idLens = Query.int().required("id")
    val sessionLens = Cookies.required("session")
    val renderer = HandlebarsTemplates().CachingClasspath()

    fun getSessionId(request: Request): Int = sessions.computeIfAbsent(
        runCatching { sessionLens(request).value }.getOrNull() ?: Random.nextInt().toString()
    ) { Random.nextInt() }

    fun Index(boardSize: Int, renderer: TemplateRenderer) =
        "/" bind GET to {
            Response(OK)
                .cookie(Cookie("session", Random.nextInt().toString()))
                .body(renderer(Index(boardSize)))
        }

    fun TapCell(appState: AppState) = "/tap" bind POST to { req: Request ->
        synchronized(appState) {
            appState.update(idLens(req), getSessionId(req))
        }
        Response(OK)
    }

    fun GameBoard(appState: AppState, renderer: TemplateRenderer) = "/game-board" bind GET to sse {
        val datastarRenderer = DatastarFragmentRenderer(renderer)

        thread {
            while (true) {
                it.sendMergeFragments(datastarRenderer(GameBoardState(appState.cells)))
                Thread.sleep(33)
            }
        }
    }

    return poly(
        TapCell(state),
        GameBoard(state, renderer),
        Index(boardSize, renderer),
    )
}
