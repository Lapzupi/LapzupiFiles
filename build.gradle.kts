import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
}

group = "dev.lapzupi.com"
version = "1.0.0"

repositories {
    mavenCentral()
    maven(
        url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    )
}

dependencies {
    compileOnly(libs.spigot.api)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
