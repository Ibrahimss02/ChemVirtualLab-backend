package com.ibrahimss.data

import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserSkinTable
import com.ibrahimss.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

class DatabaseFactory {

    init {
        Database.connect(dataSource())
        transaction {
            listOf<Table>(UserTable, SkinsTable, UserSkinTable)
                .forEach {
                    SchemaUtils.create(it)
                    SchemaUtils.createMissingTablesAndColumns(it)
                }
        }
    }

    private fun dataSource(): HikariDataSource {
        val config = HikariConfig()
        config.apply {
            driverClassName = System.getenv("JDBC_DRIVER")
//            jdbcUrl = System.getenv("DATABASE_URL")
            maximumPoolSize = 6
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            val uri = URI(System.getenv("DATABASE_URL"))
            val username = uri.userInfo.split(":").toTypedArray()[0]
            val password = uri.userInfo.split(":").toTypedArray()[1]
            jdbcUrl = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                block()
            }
        }
}