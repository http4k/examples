package com.example.demo

import org.http4k.bridge.SpringToHttp4kFallbackController
import org.http4k.core.HttpHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


//Configures the Spring Boot application to use the http4k bridge
@Configuration
class Http4kConfiguration {
    @Bean
    fun bridge(http4k: HttpHandler) = object : SpringToHttp4kFallbackController(http4k) {}
}


