plugins {
    application
    kotlin("jvm")
}

group = "com.github.ralfstuckert"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.7")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
        freeCompilerArgs.add("-Xcontext-receivers")
    }
    sourceSets {
        main {
            kotlin.srcDir("../common/src/main/kotlin")
        }
    }
}

application {
    mainClass.set("com.github.ralfstuckert.kcr.ContextReceiversDemoKt")
}
