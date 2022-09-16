val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgresql_version: String by project
val hikari_version: String by project
val jnanoid_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"

}

group = "com.ibrahimss"
version = "0.0.1"
application {
    mainClass.set("com.ibrahimss.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.create("stage") {
    dependsOn("installDist")
}

dependencies {
    implementation ("io.ktor:ktor-server-core:$ktor_version")
    implementation ("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation ("io.ktor:ktor-server-netty:$ktor_version")
    implementation ("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:$logback_version")
    testImplementation ("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //Exposed
    implementation ("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation ("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation ("org.jetbrains.exposed:exposed-dao:$exposed_version")

    //Postgresql
    implementation ("org.postgresql:postgresql:$postgresql_version")

    //Hikari
    implementation ("com.zaxxer:HikariCP:$hikari_version")

    //Util
    implementation ("com.aventrix.jnanoid:jnanoid:$jnanoid_version")
}