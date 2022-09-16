package com.ibrahimss

import kotlin.test.*
import io.ktor.server.testing.*
import com.ibrahimss.plugins.*
import io.ktor.http.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}