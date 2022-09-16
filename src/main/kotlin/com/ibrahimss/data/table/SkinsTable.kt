package com.ibrahimss.data.table

import org.jetbrains.exposed.sql.Table

object SkinsTable: Table() {
    override val tableName: String
        get() = "character_skins"

    val id = integer("skin_id").autoIncrement()
    val name = varchar("skin_name", 64)
    val price = integer("price")
    val description = varchar("description", 512)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(id)
}