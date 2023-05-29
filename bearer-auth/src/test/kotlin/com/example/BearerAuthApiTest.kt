package com.example

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class BearerAuthTest {
    private val clock = Clock.fixed(Instant.parse("2023-05-29T12:00:00Z"), ZoneOffset.UTC)
    private val http = BearerAuthApi(clock)

    @Test
    fun `verify empty jwt`() {
        val response = Request(GET, "/hello").let(http)
        response shouldHaveStatus UNAUTHORIZED
    }

    @Test
    fun `verify valid jwt`() {
        val jwt = """eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwNGsub3JnIiwiaWF0IjoxNjg1Mzg4NzgxLCJleHAiOjE3MTY5MjQ3ODEsImF1ZCI6Imh0dHA0ay5vcmciLCJzdWIiOiJ1c2VyMTIzIiwidXNlcm5hbWUiOiJIdHRwNGsgVXNlciJ9.ULNkh_qD4QvPTcMcsWEISmg5M-WDnS_TDDp0kZKheDw"""

        val response = Request(GET, "/hello")
            .header("Authorization", "Bearer $jwt")
            .let(http)

        response shouldHaveStatus OK
        response.shouldHaveBody("Hello Http4k User")
    }

    @Test
    fun `verify malformed jwt`() {
        val response = Request(GET, "/hello")
            .header("Authorization", "Bearer letmein")
            .let(http)
        response shouldHaveStatus UNAUTHORIZED
    }

    @Test
    fun `issue and verify jwt`() {
        val jwt = Request(GET, "/token/superuser")
            .let(http)
            .also { it shouldHaveStatus OK }
            .bodyString()

        Request(GET, "/hello")
            .header("Authorization", "Bearer $jwt")
            .let(http)
            .also { it shouldHaveStatus OK }
            .shouldHaveBody("Hello superuser")
    }
}
