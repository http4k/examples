package org.http4k.example

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test
import java.lang.reflect.Proxy

class EventFunctionTest {

    @Test
    fun `events are reversed and sent downstream`() {
        val sqsEvent = SQSEvent().apply {
            records = listOf(
                SQSEvent.SQSMessage().apply { body = "hello world" },
                SQSEvent.SQSMessage().apply { body = "goodbye world" }
            )
        }
        val received = mutableListOf<String>()
        val handler = EventFnHandler { req: Request ->
            received += req.bodyString()
            Response(OK)
        }
        assertThat(handler(sqsEvent, proxy()), equalTo("processed 2 messages"))

        assertThat(received, equalTo(listOf("dlrow olleh", "dlrow eybdoog")))
    }
}

inline fun <reified T> proxy(): T = Proxy.newProxyInstance(
    T::class.java.classLoader,
    arrayOf(T::class.java)
) { _, _, _ -> TODO("not implemented") } as T
