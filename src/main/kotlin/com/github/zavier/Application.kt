package com.github.zavier

import com.github.zavier.dao.DatabaseSingleton
import com.github.zavier.plugins.configureRouting
import com.github.zavier.plugins.configureSecurity
import com.github.zavier.plugins.configureSerialization
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // 初始化数据库连接等
    val config: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
    DatabaseSingleton.init(config)

    configureSecurity()
    configureRouting()
    configureSerialization()
}