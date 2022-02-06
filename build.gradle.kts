import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.gmazzo.buildconfig") version "3.0.3" // for BuildConfig: https://github.com/gmazzo/gradle-buildconfig-plugin
}

group = "com.iamvishnu.compose"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("io.ktor:ktor-client-core:1.6.7")
    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("io.ktor:ktor-client-serialization:1.6.7")
    implementation("com.alialbaali.kamel:kamel-image:0.3.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Compose-Desktop-Newsreader"
            packageVersion = "1.0.0"
        }
    }
}

buildConfig {
    buildConfigField("String", "NEWSAPI_ORG_API_KEY", "\"${System.getenv("NEWSAPI_ORG_API_KEY")}\"")
}