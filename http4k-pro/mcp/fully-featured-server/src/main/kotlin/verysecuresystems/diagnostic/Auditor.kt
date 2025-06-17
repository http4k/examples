package verysecuresystems.diagnostic

import org.http4k.core.Filter
import org.http4k.events.Events
import verysecuresystems.IncomingEvent
import verysecuresystems.OutgoingEvent

/**
 * This auditor is responsible for logging the performance of inbound calls to the system.
 */
object Auditor {

    /**
     * Audit incoming HTTP interactions
     */
    fun Incoming(events: Events) = Filter { next ->
        {
            next(it).apply { events(IncomingEvent(it.uri, status)) }
        }
    }

    /**
     * Audit outgoing HTTP interactions
     */
    fun Outgoing(events: Events) = Filter { next ->
        {
            next(it).apply { events(OutgoingEvent(it.uri, status)) }
        }
    }
}