import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.protobuf")
}

android {
    namespace = "in.kaligotla.allpermissionsimpl"
    compileSdk = 34

    defaultConfig {
        applicationId = "in.kaligotla.allpermissionsimpl"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //util
    implementation("androidx.compose.ui:ui-util:1.5.4")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:$2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Dagger Android
    implementation("com.google.dagger:dagger-android:2.37")
    implementation("com.google.dagger:dagger-android-support:2.37")
    kapt("com.google.dagger:dagger-android-processor:2.37")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    //Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //
    implementation("androidx.compose.foundation:foundation:1.5.4")

    //Accompanist
    implementation("com.google.accompanist:accompanist-navigation-animation:0.32.0")
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.32.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    //Retrofit
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("androidx.room:room-rxjava2:2.5.2")
    implementation("androidx.room:room-rxjava3:2.5.2")
    implementation("androidx.room:room-guava:2.5.2")
    testImplementation("androidx.room:room-testing:2.5.2")
    implementation("androidx.room:room-paging:2.5.2")

    //Work Manager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Work testing
    androidTestImplementation("androidx.work:work-testing:2.8.1")

    //Hit Worker
    implementation("androidx.hilt:hilt-work:1.1.0")

    //permissions
    implementation("com.google.accompanist:accompanist-permissions:0.33.2-alpha")

    //activity recognition
    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Proto DataStore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.google.protobuf:protobuf-javalite:3.21.5")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.21.5")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //Swipe refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.2-alpha")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.20.1"
    }

    plugins {
        kotlin {
//            artifact = "com.google.protobuf:protoc-gen-kotlin:1.6.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
            task.plugins {
                kotlin {}
            }
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}