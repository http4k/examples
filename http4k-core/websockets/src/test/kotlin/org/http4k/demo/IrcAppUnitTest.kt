package org.http4k.demo

import org.http4k.config.Environment
import org.http4k.core.Credentials
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.testing.testWsClient

class IrcAppUnitTest : IrcContract() {
    private val credentials = Credentials("user", "password")
    private val app = IrcApp(Environment.defaults(CREDENTIALS of credentials))

    override fun newUser() = NewUser(app.testWsClient(Request(GET, "/ws")))
}
