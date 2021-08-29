package org.http4k.example

import org.http4k.core.HttpHandler
import org.http4k.serverless.FnLoader

// The FnLoader is responsible for constructing the handler and for handling the serialisation of the request and response
fun EventFnLoader(http: HttpHandler) = FnLoader { env: Map<String, String> ->
    EventFnHandler(http)
}
