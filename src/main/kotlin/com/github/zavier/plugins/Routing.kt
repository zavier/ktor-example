package com.github.zavier.plugins

import com.github.zavier.dao.DAOFacade
import com.github.zavier.dao.DAOFacadeCacheImpl
import com.github.zavier.dao.DAOFacadeImpl
import com.github.zavier.routers.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import java.io.File

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrderRoute()
        getOrderRoute()
        totalizeOrderRoute()
        userRouting()
    }
}

val config: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
val customerDao: DAOFacade = DAOFacadeCacheImpl(
    DAOFacadeImpl(),
    File(config.property("storage.ehcacheFilePath").getString())
).apply {
    runBlocking {
        if (allCustomers().isEmpty()) {
            addNewCustomer("fir", "last", "t@e.com")
        }
    }
}