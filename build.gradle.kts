import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group = "me.kocha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    compile("io.javalin:javalin:3.13.7")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    compile("org.slf4j:slf4j-simple:1.8.0-beta4")
    compile("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation("com.beust:klaxon:5.5")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}