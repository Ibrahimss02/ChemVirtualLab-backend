val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val postgresqlVersion: String by project
val hikariVersion: String by project
val jnanoidVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.serialization") version "1.7.22"
    id("io.ktor.plugin") version "2.2.1"
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
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        name = "ktor-eap"
    }
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
}

tasks.create("stage") {
    dependsOn("installDist")
}

dependencies {
    implementation ("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation ("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation ("io.ktor:ktor-serialization-gson-jvm:$ktorVersion")
    implementation ("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation ("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation ("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    //Exposed
    implementation ("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation ("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation ("org.jetbrains.exposed:exposed-dao:$exposedVersion")

    //Postgresql
    implementation ("org.postgresql:postgresql:$postgresqlVersion")

    //Hikari
    implementation ("com.zaxxer:HikariCP:$hikariVersion")

    //Util
    implementation ("com.aventrix.jnanoid:jnanoid:$jnanoidVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.0.0-eap-256")
}