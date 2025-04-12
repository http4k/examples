package datastar

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.startsWith
import org.http4k.core.ContentType.Companion.TEXT_EVENT_STREAM
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.http4k.lens.accept
import org.http4k.lens.datastarEvents
import org.http4k.sse.SseClient
import org.http4k.sse.SseMessage
import org.http4k.testing.testHttpClient
import org.http4k.testing.testSseClient
import org.junit.jupiter.api.Test

class AppTest {

    @Test
    fun `can get index`() {
        val response: Response = app.testHttpClient(Request(GET, "/"))
        assertThat(response, hasStatus(OK).and(hasBody(startsWith("<!DOCTYPE html>"))))
    }

    @Test
    fun `can get users`() {
        app.testSseClient(Request(GET, "/users").accept(TEXT_EVENT_STREAM)).use { sse: SseClient ->
            sse.received().take(2)
                .forEach {
                    assertThat(it as SseMessage.Event, equalTo(it))
                    assertThat(it.data, startsWith("["))
                }
        }
    }

    @Test
    fun `can get users with JSON`() {
        app.testHttpClient(Request(GET, "/users")).use {
            assertThat(it, hasStatus(OK))
            assertThat(it.datastarEvents().size, equalTo(1))
        }
    }
}
