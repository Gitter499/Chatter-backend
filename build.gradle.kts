import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group = "me.kocha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val exposedVersion: String by project
dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation("io.javalin:javalin:3.13.7")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("com.beust:klaxon:5.5")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.mindrot", "jbcrypt", "0.4")
    implementation("org.apache.commons", "commons-email","1.5")
    implementation("com.auth0:java-jwt:3.17.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}