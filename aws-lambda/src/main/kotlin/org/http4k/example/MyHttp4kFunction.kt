package org.http4k.example

import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.serverless.LambdaFunction

@Suppress("unused")
class MyHttp4kFunction : LambdaFunction({ Response(OK).body("Hello World") })