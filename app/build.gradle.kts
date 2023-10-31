import java.util.Properties
import java.io.FileInputStream
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

/*fun headCommitCount(): Int {
    val cmd = "git rev-list HEAD --count"
    return cmd.runCommand().toInt()
}

fun headCommitSha(): String {
    val sha = "git rev-parse HEAD --short"
    return sha.runCommand().trim().take(8)
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
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
        versionCode = versionMajor * 1000 +
                versionMinor * 100 +
                versionPatch * 10
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "THE_GUARDIAN_API_KEY", getApiKey())
    }

    buildFeatures {
        buildConfig = true
    }

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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // activity
    implementation(libs.activity.compose)

    // jetpack compose
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

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

    // lifecycle
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
    ksp(libs.room.compiler)

    // images
    implementation(libs.coil)

    // android tests
    /*androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    kaptAndroidTest(libs.hilt.testing.compiler)*/

    // unit tests
    testImplementation(libs.bundles.unit.test)
}

