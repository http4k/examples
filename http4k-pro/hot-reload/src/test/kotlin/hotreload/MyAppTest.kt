package hotreload

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test

class MyAppTest {

    @Test
    fun `can call the app`() {
        val app = MyApp()
        assert(app(Request(GET, "/")).status == OK)
    }
}
