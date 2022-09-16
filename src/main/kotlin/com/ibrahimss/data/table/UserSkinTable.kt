package com.ibrahimss.data.table

import org.jetbrains.exposed.sql.Table

object UserSkinTable: Table() {

    override val tableName: String
        get() = "user_skin"

    val id = reference("uid", UserTable.uid)
    val skinId = reference("skin_id", SkinsTable.id)
}