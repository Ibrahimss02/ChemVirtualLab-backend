package com.ibrahimss.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserSkinTable
import com.ibrahimss.data.table.UserTable
import com.ibrahimss.model.BuySkinBody
import com.ibrahimss.model.SkinResponse
import com.ibrahimss.model.UserBody
import com.ibrahimss.model.UserResponse
import com.ibrahimss.util.mapRowToUserLiteResponse
import com.ibrahimss.util.mapRowToUserResponse
import com.ibrahimss.util.mapToSkinResponse
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class Repository {

    private var dbFactory: DatabaseFactory = DatabaseFactory()

    suspend fun buySkin(body: BuySkinBody) {
        dbFactory.dbQuery {
            UserSkinTable.insert { table ->
                table[id] = body.userId
                table[skinId] = body.skinId
            }
        }
    }

    suspend fun addNewUser(body: UserBody) {
        dbFactory.dbQuery {
            UserTable.insert { table ->
                table[uid] = "USER${NanoIdUtils.randomNanoId()}"
                table[email] = body.email
                table[name] = body.name
                table[level] = 0
                table[coin] = 0
                table[badge] = 0
            }
        }
    }

    suspend fun getUserLeaderboard() = dbFactory.dbQuery {
        UserTable.slice(
            UserTable.name,
            UserTable.coin
        ).selectAll()
            .orderBy(UserTable.coin to SortOrder.ASC)
            .mapNotNull {
                it.mapRowToUserLiteResponse()
            }
    }

    suspend fun getAllSkins() = dbFactory.dbQuery {
        SkinsTable.selectAll()
            .orderBy(SkinsTable.price to SortOrder.ASC)
            .mapNotNull {
                it.mapToSkinResponse()
            }
    }

    suspend fun getUser(userId: String): UserResponse = dbFactory.dbQuery {
        val userSkins = UserSkinTable.select{
            UserSkinTable.id eq userId
        }.mapNotNull {
            it[UserSkinTable.skinId]
        }

        val listUserSkin: MutableList<SkinResponse> = mutableListOf()
        userSkins.forEach { skinId ->
            val skin = SkinsTable.select {
                SkinsTable.id eq skinId
            }.mapNotNull {
                it.mapToSkinResponse()
            }

            listUserSkin += skin
        }

        UserTable.select{
            UserTable.uid eq userId
        }
            .firstNotNullOf {
                return@dbQuery it.mapRowToUserResponse(listUserSkin)
            }
    }
}