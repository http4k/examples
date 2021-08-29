package org.http4k.example

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.serverless.FnHandler

// This is the handler for the incoming AWS SQS event. It's just a function so you can call it without any infrastructure
fun EventFnHandler(http: HttpHandler) = FnHandler { e: SQSEvent, _: Context ->
    e.records.forEach {
        http(Request(POST, "http://localhost:8080/").body(it.body.reversed()))
    }
    "processed ${e.records.size} messages"
}
