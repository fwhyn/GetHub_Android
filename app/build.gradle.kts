plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.protobuf)
    kotlin("kapt")
}

android {
    // TODO release latest baze

    namespace = "com.fwhyn.app.gethub"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.fwhyn.app.gethub"
        minSdk = 26
        targetSdk = 36
        versionCode = 1010000
        versionName = "1.1.0"

        testInstrumentationRunner = "com.fwhyn.app.gethub.MyTestRunner"
    }

    flavorDimensions += "default"
    productFlavors {
        create("real") {
            dimension = "default"
        }

        create("fake") {
            dimension = "default"
        }
    }

    buildTypes {
        release {
            // TODO isMinifyEnabled = false for temporary until proguard is fixed before public release
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += listOf("META-INF/LICENSE-notice.md", "META-INF/LICENSE.md")
        }
    }
}

dependencies {
    // Main Dependencies
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.fwhyn.lib.baze)
    implementation(libs.org.apache.poi.ooxml)
    implementation(libs.com.squareup.okhttp3.mockwebserver)
    implementation(libs.bundles.datastore)
    implementation(libs.com.google.protobuf.javalite)
    implementation(libs.androidx.security.crypto.ktx)

    //// Retrofit
    implementation(libs.bundles.retrofit2)
    implementation(libs.bundles.okhttp)

    //// Compose
    implementation(libs.bundles.androidx.compose)
    implementation(platform(libs.androidx.compose.bom))

    /// Dagger Hilt
    implementation(libs.bundles.dagger.hilt)
    kapt(libs.bundles.dagger.hilt.compiler)
    annotationProcessor(libs.bundles.dagger.hilt.compiler)

    // Testing Dependencies
    testImplementation(libs.io.mock)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.app.cash.turbine)

    androidTestImplementation(libs.androidx.runner)

    //// MockWebServer Test
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
    androidTestImplementation(libs.com.squareup.okhttp3.mockwebserver)

    //// Coroutine Test
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.org.jetbrains.kotlinx.coroutines.test)

    //// JUnit Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //// Compose Test
    androidTestImplementation(libs.bundles.androidx.compose.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    //// Dagger Hilt Test
    testImplementation(libs.com.google.dagger.hilt.android.testing)
    kaptTest(libs.bundles.dagger.hilt.compiler)
    testAnnotationProcessor(libs.bundles.dagger.hilt.compiler)
    androidTestImplementation(libs.com.google.dagger.hilt.android.testing)
    kaptAndroidTest(libs.bundles.dagger.hilt.compiler)
    androidTestAnnotationProcessor(libs.bundles.dagger.hilt.compiler)
}

protobuf {
    protoc { artifact = libs.com.google.protobuf.protoc.get().toString() }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") { option("lite") }
            }
        }
    }
}