package org.http4k.examples

import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.singlePageApp

fun Http4kReactApp() = singlePageApp(Classpath("reactapp"))
