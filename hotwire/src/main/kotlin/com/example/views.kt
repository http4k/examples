package com.example

import org.http4k.template.ViewModel

data class Greeting(val person: String) : ViewModel

data class Ping(val pingTime: Int) : ViewModel

data class Load(val at: Int, val avg: Int) : ViewModel

object Index : ViewModel
