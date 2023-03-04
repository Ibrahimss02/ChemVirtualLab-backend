package com.ibrahimss.data

import com.ibrahimss.data.table.SkinsTable
import com.ibrahimss.data.table.UserSkinTable
import com.ibrahimss.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcUrl = config.property("storage.jdbcURL").getString()
        val database = Database.connect(createHikariDataSource(jdbcUrl, driverClassName))

        transaction(database) {
            listOf(UserTable, SkinsTable, UserSkinTable)
                .forEach {
                    SchemaUtils.create(it)
                    SchemaUtils.createMissingTablesAndColumns(it)
                }
        }
    }

    private fun createHikariDataSource(url: String, driver: String) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 6
        username = "postgres"
        password = "Admin2002"
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    })

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                block()
            }
        }
}