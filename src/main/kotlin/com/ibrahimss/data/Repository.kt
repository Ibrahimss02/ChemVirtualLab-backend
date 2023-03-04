package com.ibrahimss.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.ibrahimss.data.DatabaseFactory.dbQuery
import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserSkinTable
import com.ibrahimss.data.table.UserTable
import com.ibrahimss.model.*
import com.ibrahimss.util.mapRowToUserLiteResponse
import com.ibrahimss.util.mapRowToUserResponse
import com.ibrahimss.util.mapRowToUserSkinResponse
import com.ibrahimss.util.mapToSkinResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class Repository {

    suspend fun buySkin(body: BuySkinBody) = dbQuery {
        val insertStatement = UserSkinTable.insert { table ->
            table[id] = body.userId
            table[skinId] = body.skinId
        }

        insertStatement.resultedValues?.singleOrNull()?.get(UserSkinTable.id)

    }

    suspend fun addNewUser(body: NewUserBody) = dbQuery {
        val insertStatement = UserTable.insert { table ->
            table[uid] = body.uid
            table[email] = body.email
            table[name] = body.name
            table[exp] = 0
            table[level] = 1
            table[coin] = 0
            table[badge] = 0
        }

        insertStatement.resultedValues?.singleOrNull()?.get(UserTable.uid)
    }


    suspend fun updateUser(uid: String, body: UserBody) = dbQuery {
        UserTable.update(where = { UserTable.uid eq uid }) { table ->
            table[email] = body.email
            table[name] = body.name
            table[exp] = body.exp
            table[level] = body.level
            table[coin] = body.coin
            table[badge] = body.badge
        } > 0
    }


    suspend fun getUserLeaderboard() = dbQuery {
        UserTable.slice(
            UserTable.name,
            UserTable.coin
        ).selectAll()
            .orderBy(UserTable.coin to SortOrder.ASC)
            .mapNotNull {
                it.mapRowToUserLiteResponse()
            }
    }

    suspend fun getAllSkins() = dbQuery {
        SkinsTable.selectAll()
            .orderBy(SkinsTable.price to SortOrder.ASC)
            .mapNotNull {
                it.mapToSkinResponse()
            }
    }

    suspend fun getUser(userId: String): UserResponse = dbQuery {
        UserTable.select {
            UserTable.uid eq userId
        }
            .firstNotNullOf {
                it.mapRowToUserResponse()
            }
    }

    suspend fun getUserSkin(userId: String): UserSkinResponse = dbQuery {
        val userSkins = UserSkinTable.select {
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

        UserTable.select {
            UserTable.uid eq userId
        }
            .firstNotNullOf {
                it.mapRowToUserSkinResponse(listUserSkin)
            }
    }
}