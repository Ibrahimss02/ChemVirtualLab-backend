package com.ibrahimss.route

import io.ktor.resources.*
import kotlinx.serialization.Serializable

sealed class RouteLocation {

    @Serializable
    @Resource("/user")
    class User() {
        @Serializable
        @Resource("{userId}")
        class Id(val parent: User = User(), val userId: String)

        @Serializable
        @Resource("leaderboard")
        class Leaderboard(val parent: User = User())
    }

    @Serializable
    @Resource("/skins")
    class Skin() {
        @Serializable
        @Resource("buy")
        class Buy(val parent: Skin = Skin())
    }
}
