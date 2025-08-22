import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}
val keyStoreProperties = Properties().apply {
    val keyStorePropertiesFile = rootProject.file("keystore.properties")
    if (keyStorePropertiesFile.exists()) {
        load(keyStorePropertiesFile.inputStream())
    }
}

val googleClientId: String = localProperties.getProperty("GOOGLE_CLIENT_ID") ?: "\"\""
val exchangeRateApiKey: String = localProperties.getProperty("EXCHANGE_RATE_API_KEY") ?: "\"\""

val storeFilePath: String = keyStoreProperties.getProperty("STORE_FILE_PATH")
val storePassword: String = keyStoreProperties.getProperty("STORE_PASSWORD")
val keyAlias: String = keyStoreProperties.getProperty("KEY_ALIAS")
val keyPassword: String = keyStoreProperties.getProperty("KEY_PASSWORD")

android {
    signingConfigs {
        create("release") {
            storeFile = file(storeFilePath)
            storePassword = storePassword
            keyAlias = keyAlias
            keyPassword = keyPassword
        }
    }
    namespace = "com.fintern.ourbudgeting"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fintern.ourbudgeting"
        minSdk = 26
        targetSdk = 35
        versionCode = 70
        versionName = "0.7.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"$googleClientId\"")
        buildConfigField("String", "EXCHANGE_RATE_API_KEY", "\"$exchangeRateApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Material Icon
    implementation(libs.androidx.material.icons.extended)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)

    // Google Auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Image
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // ML Kit
    implementation(libs.text.recognition.korean)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Network
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)
}