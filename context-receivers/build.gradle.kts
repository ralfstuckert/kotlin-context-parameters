plugins {
    kotlin("jvm") version "2.1.20"
}

group = "com.github.ralfstuckert"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}