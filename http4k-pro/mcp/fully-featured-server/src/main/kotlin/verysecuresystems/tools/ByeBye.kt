package verysecuresystems.tools

import org.http4k.ai.mcp.ToolResponse.Error
import org.http4k.ai.mcp.ToolResponse.Ok
import org.http4k.ai.mcp.model.Tool
import org.http4k.ai.mcp.server.capability.ToolCapability
import org.http4k.lens.with
import org.http4k.routing.bind
import verysecuresystems.Message
import verysecuresystems.UserEntry
import verysecuresystems.Username

/**
 * Allows users to exit the building, but only if they are already inside.
 */
fun ByeBye(
    removeUser: (Username) -> Boolean,
    entryLogger: (Username) -> UserEntry
): ToolCapability = Tool("bye", "User exits the building") bind { req ->
    val exiting = username(req)
    if (removeUser(exiting)) {
        entryLogger(exiting)
        Ok().with(message of Message.of("processing"))
    } else Error(404, "User is not inside building")
}
