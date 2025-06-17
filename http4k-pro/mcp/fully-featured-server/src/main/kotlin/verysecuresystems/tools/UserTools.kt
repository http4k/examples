package verysecuresystems.tools

import org.http4k.ai.mcp.server.capability.CapabilityPack
import verysecuresystems.Inhabitants
import verysecuresystems.external.EntryLogger
import verysecuresystems.external.UserDirectory

/**
 * A CapabilityPack collapses several capabilities together.
 */
fun UserTools(
    userDirectory: UserDirectory,
    entryLogger: EntryLogger,
    inhabitants: Inhabitants
) = CapabilityPack(
    KnockKnock(userDirectory::lookup, inhabitants::add, entryLogger::enter),
    WhoIsThere(inhabitants, userDirectory::lookup),
    ByeBye(inhabitants::remove, entryLogger::exit)
)
