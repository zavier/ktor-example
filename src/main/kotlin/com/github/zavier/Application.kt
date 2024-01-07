package com.github.zavier

import com.github.zavier.plugins.configureRouting
import com.github.zavier.plugins.configureSecurity
import com.github.zavier.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
    configureSerialization()
}