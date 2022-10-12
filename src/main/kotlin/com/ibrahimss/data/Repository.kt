package com.ibrahimss.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserSkinTable
import com.ibrahimss.data.table.UserTable
import com.ibrahimss.model.*
import com.ibrahimss.util.mapRowToUserLiteResponse
import com.ibrahimss.util.mapRowToUserResponse
import com.ibrahimss.util.mapToSkinResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

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

    suspend fun addNewUser(body: NewUserBody) {
        dbFactory.dbQuery {
            UserTable.insert { table ->
                table[uid] = "USER${NanoIdUtils.randomNanoId()}"
                table[email] = body.email
                table[name] = body.name
                table[exp] = 0
                table[level] = 1
                table[coin] = 0
                table[badge] = 0
            }
        }
    }

    suspend fun updateUser(body: UserBody) {
        dbFactory.dbQuery {
            UserTable.update({ UserTable.uid eq body.id }) { table ->
                table[email] = body.email
                table[name] = body.name
                table[level] = body.level
                table[coin] = body.coin
                table[exp] = body.exp
                table[badge] = body.badge
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