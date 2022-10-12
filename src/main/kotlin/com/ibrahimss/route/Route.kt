package com.ibrahimss.route

import com.ibrahimss.data.Repository
import com.ibrahimss.model.BuySkinBody
import com.ibrahimss.model.NewUserBody
import com.ibrahimss.model.UserBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.resources.post
import io.ktor.server.routing.Route

class Route {

    private val repository = Repository()

    suspend inline fun <reified T> ApplicationCall.response(noinline action: suspend () -> T) {
        try {
            this.respond(
                HttpStatusCode.OK,
                action()!!
            )
        } catch (e: Exception) {
            this@response.error(e)
        }
    }

    suspend inline fun ApplicationCall.error(exception: Exception) {
        when (exception) {
            is BadRequestException -> this.respond(HttpStatusCode.BadRequest, exception)
            is NotFoundException -> this.respond(HttpStatusCode.NotFound, exception)
            else -> this.respond(HttpStatusCode.Conflict, exception)
        }
    }

    private fun Route.getUser() {
        get<RouteLocation.User.Id> {
            val userId = try {
                call.parameters["userId"]
            } catch (e: Exception) {
                call.error(e)
                return@get
            } ?: ""

            call.response {
                repository.getUser(userId)
            }
        }
    }

    private fun Route.updateUser() {
        post<RouteLocation.User.Id> {
            val body = try {
                call.receive<UserBody>()
            } catch (e: Exception) {
                call.error(e)
                return@post
            }

            call.response {
                repository.updateUser(body)
            }
        }
    }

    private fun Route.getAllSkins() {
        get<RouteLocation.Skin> {
            call.response {
                repository.getAllSkins()
            }
        }
    }

    private fun Route.getLeaderboard() {
        get<RouteLocation.User.Leaderboard> {
            call.response {
                repository.getUserLeaderboard()
            }
        }
    }

    private fun Route.addSkinToUserInventory() {
        post<RouteLocation.Skin.Buy> {
            val body = try {
                call.receive<BuySkinBody>()
            } catch (e: Exception) {
                call.error(e)
                return@post
            }

            call.response {
                repository.buySkin(body)
            }
        }
    }

    private fun Route.addNewUser() {
        post<RouteLocation.User> {
            val body = try {
                call.receive<NewUserBody>()
            } catch (e: Exception) {
                call.error(e)
                return@post
            }

            call.response {
                repository.addNewUser(body)
            }
        }
    }

    fun Route.initAllRoutes() {
        getUser()
        getAllSkins()
        getLeaderboard()
        addSkinToUserInventory()
        addNewUser()
        updateUser()
    }
}