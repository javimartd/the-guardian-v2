import android.annotation.SuppressLint
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

/*fun headCommitCount() {
    val cmd = "git rev-list HEAD --count"
    return cmd.execute.text.toInteger()
}

fun headCommitSha() {
    val sha = "git rev-parse HEAD --short"
    return sha.execute().text.trim().take(8)
}*/

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0
//val versionBuild = headCommitCount()

fun getApiKey(): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties["THE_GUARDIAN_API_KEY"] as String
}

android {
    namespace = "com.javimartd.theguardian.v2"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.javimartd.theguardian.v2"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
        versionCode = versionMajor * 1000 +
                versionMinor * 100 +
                versionPatch * 10
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "THE_GUARDIAN_API_KEY", getApiKey())
    }

    /*testOptions {
        unitTests.returnDefaultValues = true
    }*/

    signingConfigs {
        create("release") {
            try {
                /*storeFile = file(project.property("THE_GUARDIAN_STORE_FILE"))
                storePassword = project.property("THE_GUARDIAN_STORE_PASSWORD")
                keyAlias = project.property("THE_GUARDIAN_APP_KEY_ALIAS")
                keyPassword = project.property("THE_GUARDIAN_APP_KEY_PASSWORD")*/
            } catch (e: Exception) {
                throw InvalidUserDataException("You should define THE_GUARDIAN_STORE and THE_GUARDIAN_APP_KEY in gradle.properties. " + e.message)
            }
        }
    }

    flavorDimensions.add("mode")
    productFlavors {
        create("mock") {
            dimension = "mode"
            applicationIdSuffix = ".mock"
            versionNameSuffix = "-mock"
        }
        create("full") {
            dimension = "mode"
            applicationIdSuffix = ".full"
            versionNameSuffix = "-full"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // activity
    implementation(libs.activity.ktx)

    // android kotlin extensions
    implementation(libs.core.ktx)

    // jetpack compose
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    // swipe refresh layout
    implementation(libs.accompanist)

    // navigation
    implementation(libs.navigation)

    // retrofit
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)

    // okhttp
    implementation(libs.okhttp3)
    implementation(libs.logging.interceptor)

    // coroutines
    implementation(libs.coroutines)

    //lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.extensions)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // images
    implementation(libs.coil)

    // android tests
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.core.testing)

    // unit tests
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito)
    testImplementation(libs.core.testing)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.tngtech.archunit)
}

