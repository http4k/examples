package verysecuresystems

import dev.forkhandles.values.IntValue
import dev.forkhandles.values.IntValueFactory
import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.events.Event
import org.http4k.events.EventCategory

data class IncomingEvent(val uri: Uri, val status: Status) : Event {
    val category = EventCategory("incoming")
}

data class OutgoingEvent(val uri: Uri, val status: Status) : Event {
    val category = EventCategory("outgoing")
}

class Id private constructor(value: Int) : IntValue(value) {
    companion object : IntValueFactory<Id>(::Id)
}

class Username private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<Username>(::Username)
}

class EmailAddress private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<EmailAddress>(::EmailAddress)
}

data class User(val id: Id, val name: Username, val email: EmailAddress)

data class UserEntry(val username: String, val goingIn: Boolean, val timestamp: Long)

class Message private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<Message>(::Message)
}
