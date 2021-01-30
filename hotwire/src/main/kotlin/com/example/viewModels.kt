package com.example

import org.http4k.template.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle.MEDIUM

data class HelloWorld(val person: String) : ViewModel

class Clicks(raw: LocalDateTime) : ViewModel {
    val time = raw.format(timeFormat)
}

class Time(raw: LocalDateTime) : ViewModel {
    val time = raw.format(timeFormat)
}

object Index : ViewModel

private val timeFormat = DateTimeFormatter.ofLocalizedDateTime(MEDIUM)
