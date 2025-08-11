plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt)
    kotlin("kapt")
}

android {
    // TODO correct system design doc
    // TODO add ui test
    // TODO add integrated test
    // TODO add unit test
    // TODO check warnings
    // TODO add app icon

    namespace = "com.fwhyn.app.gethub"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.fwhyn.app.gethub"
        minSdk = 26
        targetSdk = 36
        versionCode = 1000000
        versionName = "1.0.0"

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
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.com.squareup.okhttp3.mockwebserver)

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
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.org.robolectric)
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)

    androidTestImplementation(libs.androidx.runner)

    /// JUnit Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    //// Compose Testing
    androidTestImplementation(libs.bundles.androidx.compose.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    /// Dagger Hilt Testing
    testImplementation(libs.com.google.dagger.hilt.android.testing)
    kaptTest(libs.bundles.dagger.hilt.compiler)
    testAnnotationProcessor(libs.bundles.dagger.hilt.compiler)
    androidTestImplementation(libs.com.google.dagger.hilt.android.testing)
    kaptAndroidTest(libs.bundles.dagger.hilt.compiler)
    androidTestAnnotationProcessor(libs.bundles.dagger.hilt.compiler)
}