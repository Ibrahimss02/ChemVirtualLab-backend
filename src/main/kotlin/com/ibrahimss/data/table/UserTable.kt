package com.ibrahimss.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    override val tableName: String
        get() = "user"

    val uid = varchar("uid", 128)
    val email = varchar("email", 128)
    val name = varchar("name", 128)
    val exp = integer("exp").default(0)
    val level = integer("level")
    val coin = integer("coin")
    val badge = integer("badge")

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(uid)
}