package com.example

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class WebContentTest {
    @Test
    fun `can load index`() {
        val app = WebContent(Clock.fixed(Instant.EPOCH, ZoneId.of("UTC")), false)
        assertEquals(app(Request(GET, "/")).bodyString().contains("Welcome to the http4k Web Content example"), true)
    }
}
