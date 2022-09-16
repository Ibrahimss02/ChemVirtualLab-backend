package com.ibrahimss

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.ibrahimss.plugins.*
import io.ktor.server.application.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}