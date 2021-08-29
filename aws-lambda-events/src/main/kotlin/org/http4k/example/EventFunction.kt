package org.http4k.example

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.serverless.AwsLambdaEventFunction

// we will "send" reversed events to this HttpHandler
val fakeReceiver = { req: Request ->
    println(req.bodyString())
    Response(Status.OK)
}

// This class is the entry-point for the Lambda function call - configure it when deploying
class EventFunction : AwsLambdaEventFunction(EventFnLoader(fakeReceiver))
