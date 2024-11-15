package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasContentType
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.random.Random

class JsonApiTest {
    @Test
    fun `JSON endpoint returns json`() {
        val app = JsonApi(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")), Random(0))

        assertThat(app(Request(GET, "/")),
            hasStatus(OK)
                .and(hasContentType(APPLICATION_JSON))
                .and(hasBody("""{"now":"1970-01-01T00:00:00Z","randomNumber":-1934310868,"bool":true}"""))
        )
    }

    @Test
    fun `JSON is echoed`() {
        val app = JsonApi(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")), Random(0))

        assertThat(app(Request(POST, "/echo").body("""{"now":"1970-01-01T00:00:00Z","randomNumber":-1934310868,"bool":true}""")),
            hasStatus(OK)
                .and(hasContentType(APPLICATION_JSON))
                .and(hasBody("""{"now":"1970-01-01T00:00:00Z","randomNumber":-1934310868,"bool":true}"""))
        )
    }
}
