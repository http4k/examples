package verysecuresystems.tools

import org.http4k.ai.mcp.ToolResponse.Ok
import org.http4k.ai.mcp.model.Tool
import org.http4k.ai.mcp.server.capability.ToolCapability
import org.http4k.ai.mcp.util.McpJson.auto
import org.http4k.lens.with
import org.http4k.routing.bind
import verysecuresystems.EmailAddress
import verysecuresystems.Id
import verysecuresystems.User
import verysecuresystems.Username

/**
 * Retrieves a list of the users inside the building.
 */
fun WhoIsThere(inhabitants: Iterable<Username>, lookup: (Username) -> User?): ToolCapability =
    Tool("whoIsThere", "Check who is in the building") bind { req ->
        Ok().with(users of inhabitants.mapNotNull(lookup))
    }

val users = Tool.Output.auto(
    listOf(User(Id.of(1), Username.of("user"), EmailAddress.of("user@gmail.com")))
)
    .toLens("the list of users currently in the building")
