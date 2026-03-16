pluginManagement {
    plugins {
        kotlin("jvm") version "2.3.20"
    }
}
rootProject.name = "kotlin-context-parameters"
include("context-parameters")
include("talk")
include("common")
includeBuild("context-receivers")
