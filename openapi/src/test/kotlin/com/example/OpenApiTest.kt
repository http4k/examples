package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasContentType
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.random.Random

class OpenApiTest {
    private val unsecuredApp = OpenApi(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")), Random(0))
    private val securedApp = ClientFilters.BasicAuth("http4k", "http4k").then(unsecuredApp)

    @Test
    fun `can load open api doc`() {
        assertThat(unsecuredApp(Request(GET, "/")),
            hasStatus(OK).and(hasBody(containsSubstring(""""title":"http4k Open API Example""""))))
    }

    @Test
    fun `hello endpoint is unsecured`() {
        assertThat(unsecuredApp(Request(GET, "/hello")),
            hasStatus(OK).and(hasBody(containsSubstring("""hello world!"""))))
    }

    @Test
    fun `JSON endpoint is secured`() {
        assertThat(unsecuredApp(Request(GET, "/json")), hasStatus(UNAUTHORIZED))
    }

    @Test
    fun `JSON endpoint returns json`() {
        assertThat(securedApp(Request(GET, "/json")),
            hasStatus(OK)
                .and(hasContentType(APPLICATION_JSON))
                .and(hasBody("""{"customHeader":null,"now":"1970-01-01T00:00:00Z","randomNumber":-1934310868,"bool":true}"""))
        )
    }
}
