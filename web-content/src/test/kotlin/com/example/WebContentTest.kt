package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class WebContentTest {
    @Test
    fun `can load index`() {
        val app = WebContent(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")), false)
        assertThat(app(Request(GET, "/")),
            hasStatus(OK) and hasBody(containsSubstring("Welcome to the http4k Web Content example")))
    }
}
