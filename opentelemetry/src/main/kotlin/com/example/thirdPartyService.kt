package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun ThirdPartyApp(): HttpHandler = {
    Response(OK).body(it.bodyString() + it.bodyString())
}

fun ThirdPartyServer(port: Int) =
    PrintRequestAndResponse(System.err)
        .then(ThirdPartyApp())
        .asServer(SunHttp((port)))

