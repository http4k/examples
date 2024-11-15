package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class HelloWorldTest {
    @Test
    fun `App says hello!`() {
        val app = HelloWorld()
        assertThat(app(Request(GET, "/")), hasStatus(OK) and hasBody("hello world!"))
    }
}
