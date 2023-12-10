// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.0.2" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}