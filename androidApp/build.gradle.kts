import java.util.Properties

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("com.google.gms.google-services")
}

android {
    namespace = "io.readingstats.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "io.readingstats.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            buildConfigField("String", "DEV_EMAIL", "\"${localProperties["DEV_EMAIL"] ?: ""}\"")
            buildConfigField(
                "String",
                "DEV_PASSWORD",
                "\"${localProperties["DEV_PASSWORD"] ?: ""}\""
            )
            buildConfigField(
                "String",
                "TURSO_DATABASE_URL",
                "\"${localProperties["TURSO_DATABASE_URL"] ?: ""}\""
            )
            buildConfigField(
                "String",
                "TURSO_AUTH_TOKEN",
                "\"${localProperties["TURSO_AUTH_TOKEN"] ?: ""}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.navigation.compose)
    implementation(libs.ui.text.google.fonts)
    implementation(libs.libsql)
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
