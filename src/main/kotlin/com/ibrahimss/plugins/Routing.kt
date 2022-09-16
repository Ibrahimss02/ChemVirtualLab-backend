package com.ibrahimss.plugins

import com.ibrahimss.route.Route
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val route = Route()

    install(Resources)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route.apply {
            initAllRoutes()
        }
    }
}
