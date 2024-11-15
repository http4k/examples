package com.example

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.throws
import org.http4k.config.Environment.Companion.EMPTY
import org.http4k.config.Environment.Companion.from
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.hamkrest.hasBody
import org.http4k.lens.LensFailure
import org.junit.jupiter.api.Test

class HelloWorldTest {

    @Test
    fun `fails if there is missing environment configuration`() {
        assertThat({ HelloWorld(EMPTY) },
            throws(has(LensFailure::getLocalizedMessage, containsSubstring("env 'TIMEOUT' is required"))))
    }

    @Test
    fun `fails for invalid environment configuration`() {
        assertThat({ HelloWorld(from("TIMEOUT" to "123")) },
            throws(has(LensFailure::getLocalizedMessage, containsSubstring("env 'TIMEOUT' must be string"))))
    }

    @Test
    fun `reports configuration`() {
        val app = HelloWorld(from("TIMEOUT" to "PT2S"))
        assertThat(app(Request(GET, "")),
            hasBody("Uri was configured with: https://github.com, Timeout was configured with PT2S"))
    }
}
