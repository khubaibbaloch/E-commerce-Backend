plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.1.10"
}

group = "com.commerce"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    val ktor_version = "3.2.0"
    implementation("io.ktor:ktor-server-auth:2.3.7")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.7")
    implementation("com.auth0:java-jwt:4.4.0")


    // Logging
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    // Status Page
    implementation("io.ktor:ktor-server-status-pages:2.3.4")

    implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
    // Clint Side
    implementation("io.ktor:ktor-client-content-negotiation:3.2.0")
    // Server Side
    implementation("io.ktor:ktor-server-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Exposed core components
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")

    // H2 database driver
    implementation("com.h2database:h2:2.2.224")

    // HikariCP for connection pooling
    implementation("com.zaxxer:HikariCP:5.1.0")


    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-ktor:3.5.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.3")


    // PostgreSQL Driver
    implementation("org.postgresql:postgresql:42.7.1")


    implementation("io.ktor:ktor-client-apache") // or CIO
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    // HASH
    implementation("at.favre.lib:bcrypt:0.9.0")

    // ENV
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}
