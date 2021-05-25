package org.http4k.demo

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.websocket.WsClient
import org.http4k.websocket.WsMessage
import org.junit.jupiter.api.Test

class NewUser(private val client: WsClient) {
    fun leaves() = client.close()

    fun sends(message: String) = client.send(WsMessage(message))

    fun receives(vararg messages: String) =
        assertThat(client.received()
            .take(messages.size)
            .map(WsMessage::bodyString)
            .toList(), equalTo(messages.toList())
        )
}

abstract class IrcContract {
    abstract fun newUser(): NewUser

    @Test
    fun `users get expected messages when they interact`() {
        val user1 = newUser()
        user1.receives("user1 joined")
        user1.sends("hello!")
        user1.receives("user1: hello!")

        val user2 = newUser()
        user1.receives("user2 joined")

        user2.receives("user1 joined", "user1: hello!", "user2 joined")
        user2.sends("hello user1!")

        user1.receives("user2: hello user1!")
        user2.receives("user2: hello user1!")

        user1.leaves()

        user2.receives("user1 left")
    }
}