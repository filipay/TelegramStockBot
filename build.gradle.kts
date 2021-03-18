import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.4.30"
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

application {
    mainClass.set("AppKt")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.15.0")
    implementation("org.telegram:telegrambots:5.0.1")
    implementation("org.springframework:spring-core:5.3.3")
    implementation("org.springframework:spring-context:5.3.3")
    implementation("org.knowm.xchange:xchange-kraken:5.0.6")
    implementation("org.apache.logging.log4j:log4j-api:2.14.0")
    implementation("org.apache.logging.log4j:log4j-core:2.14.0")
    implementation("com.influxdb:influxdb-client-kotlin:2.0.0")

    testImplementation("io.mockk:mockk:1.10.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("org.springframework:spring-test:5.3.3")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

project.setProperty("mainClassName", "AppKt")
tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("shadow")
}
