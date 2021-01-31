plugins {
    java
    kotlin("jvm") version "1.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
    test {
        java.srcDir("src/main/kotlin")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")

    testImplementation("io.mockk:mockk:1.10.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
