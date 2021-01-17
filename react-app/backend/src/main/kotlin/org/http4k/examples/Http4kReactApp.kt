package org.http4k.examples

import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.singlePageApp

fun Http4kReactApp() =
    PrintRequestAndResponse()
        .then(singlePageApp(Classpath("reactapp")))

