package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.startsWith
import environment.oauthserver.SimpleOAuthServer
import org.http4k.core.Credentials
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.TEMPORARY_REDIRECT
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class HellOAuthTest {

    private val app = HellOAuth(Uri.of("http://auth"), 
        Credentials("http4kServer", "http4kServerSecret"), { Response(OK) })

    @Test
    fun `root url is secured with oauth`() {
        assertThat(app(Request(GET, "/")),
            hasStatus(TEMPORARY_REDIRECT).and(hasHeader("location", startsWith("http://auth"))))
    }

    @Test
    fun `root url authed with access token is ok`() {
        val authedApp = ClientFilters.BearerAuth("ACCESS_TOKEN_123").then(app)
        assertThat(authedApp(Request(GET, "/")), hasStatus(OK).and(hasBody("hello world!")))
    }
}
