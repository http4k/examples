package verysecuresystems.tools

import org.http4k.ai.mcp.ToolResponse.Error
import org.http4k.ai.mcp.ToolResponse.Ok
import org.http4k.ai.mcp.model.Tool
import org.http4k.ai.mcp.model.value
import org.http4k.ai.mcp.server.capability.ToolCapability
import org.http4k.ai.mcp.util.McpJson.auto
import org.http4k.lens.with
import org.http4k.routing.bind
import verysecuresystems.Message
import verysecuresystems.User
import verysecuresystems.UserEntry
import verysecuresystems.Username

/**
 * Allows users to enter the building, but only if they exist.
 */
fun KnockKnock(
    lookup: (Username) -> User?,
    add: (Username) -> Boolean,
    entryLogger: (Username) -> UserEntry
): ToolCapability =
    Tool("knockKnock", "Register an entrant") bind { req ->
        lookup(username(req))?.name
            ?.let {
                if (add(it)) {
                    entryLogger(it)
                    Ok().with(message of Message.of("Access granted"))
                } else {
                    Ok().with(message of Message.of("User is already inside building"))
                }
            }
            ?: Error(404, "Unknown user")
    }

val username = Tool.Arg.value(Username).required("username")
val message = Tool.Output.auto<Message>(Message.of("example")).toLens("the message")
