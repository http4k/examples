package hotreload

import org.http4k.core.HttpHandler
import org.http4k.hotreload.HotReloadServer
import org.http4k.hotreload.HotReloadable

// This class is needed to create a HotReloadable instance of the HttpHandler
class ReloadableHttpApp : HotReloadable<HttpHandler> {
    override fun create() = MyApp()
}

fun main() {
    // Start the HotReloadServer - configuration is available for non-standard setups
    HotReloadServer.http<ReloadableHttpApp>().start()
}
