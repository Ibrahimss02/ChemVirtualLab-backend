package com.ibrahimss.util

import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserTable
import com.ibrahimss.model.SkinResponse
import com.ibrahimss.model.UserLiteResponse
import com.ibrahimss.model.UserResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.mapRowToUserResponse(skins: List<SkinResponse>) = UserResponse(
    uid = this[UserTable.uid],
    email = this[UserTable.email],
    name = this[UserTable.name],
    level = this[UserTable.level],
    coins = this[UserTable.coin],
    badge = this[UserTable.badge],
    skins = skins
)

fun ResultRow.mapRowToUserLiteResponse() = UserLiteResponse(
    name = this[UserTable.name],
    coins = this[UserTable.coin]
)

fun ResultRow.mapToSkinResponse() = SkinResponse(
    skinId = this[SkinsTable.id],
    name = this[SkinsTable.name],
    price = this[SkinsTable.price],
    description = this[SkinsTable.description]
)