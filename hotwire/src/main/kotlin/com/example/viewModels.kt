package com.example

import org.http4k.template.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle.MEDIUM

data class HelloWorld(val person: String) : ViewModel

class Clicks(raw: LocalDateTime) : ViewModel {
    val time = raw.format(DateTimeFormatter.ofLocalizedDateTime(MEDIUM))
}

class Time(raw: LocalDateTime) : ViewModel {
    val time = raw.format(DateTimeFormatter.ofLocalizedDateTime(MEDIUM))
}

object Index : ViewModel
