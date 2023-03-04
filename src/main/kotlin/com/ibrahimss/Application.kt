package com.ibrahimss

import com.ibrahimss.data.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.ibrahimss.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)
    configureRouting()
    configureSerialization()
}