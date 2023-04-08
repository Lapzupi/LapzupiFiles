pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    
}
rootProject.name = "LapzupiFiles"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("spigot-api", "org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
        }
    }
}