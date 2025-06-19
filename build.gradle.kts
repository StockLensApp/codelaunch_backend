plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.serialization)
}

group = "app.stocklens"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.spring.boot.bom))
    // Spring Boot
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.security.oauth2.jose)
    implementation(libs.spring.retry)

    // Kotlin
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.jdk8)
    // Jackson
    implementation(libs.jackson.kotlin)

    // AWS SDK
    implementation(platform(libs.aws.sdk.bom))
    implementation(libs.aws.sdk.kotlin.secretsmanager)
    implementation(libs.aws.sdk.s3)
    implementation(libs.aws.sdk.auth)
    implementation(libs.aws.sdk.cloudwatch)
    implementation(libs.aws.sdk.kotlin.sqs)
    implementation(libs.aws.sdk.kotlin.dynamodb)

    implementation(libs.aws.smithy.kotlin.http)

    // Logging
    implementation(libs.logback.classic)
    implementation(libs.slf4j.api)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}


tasks.withType<Jar> {
    enabled = true
    archiveBaseName.set("codelaunch_backend")
    archiveVersion.set("1.0-SNAPSHOT")
}

springBoot {
    mainClass.set("app.stocklens.ApplicationKt")
}
