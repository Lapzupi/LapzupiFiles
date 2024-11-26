import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("maven-publish")
    kotlin("jvm") version "2.1.0-RC2"
}

group = "com.lapzupi.dev.files"
version = "1.1.0"

dependencies {
    compileOnly(libs.paper.api)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupId
            artifactId = artifactId
            version = version
            
            from(components["java"])
            artifact(tasks.named("sourcesJar"))
        }
    }
}
