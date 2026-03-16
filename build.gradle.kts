plugins {
    base
}

group = "com.github.ralfstuckert"
version = "1.0-SNAPSHOT"

allprojects {
    group = rootProject.group
    version = rootProject.version
}

val contextReceiversBuild = tasks.register("contextReceiversBuild") {
    group = "build"
    description = "Builds the legacy context-receivers demo with its standalone compiler setup."
    dependsOn(gradle.includedBuild("context-receivers").task(":build"))
}

tasks.register("runContextReceivers") {
    group = "application"
    description = "Runs the context-receivers demo."
    dependsOn(gradle.includedBuild("context-receivers").task(":run"))
}

tasks.register("runContextParameters") {
    group = "application"
    description = "Runs the context-parameters demo."
    dependsOn(":context-parameters:run")
}

tasks.named("build") {
    dependsOn(":context-parameters:build", ":talk:build", contextReceiversBuild)
}
