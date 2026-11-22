plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.dendy.os"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dendy.os"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.1.0-OMODA"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:vehicle"))
    implementation(project(":core:ai"))
    implementation(project(":core:media"))
    implementation(project(":core:weather"))
    implementation(project(":core:installer"))
    implementation(project(":feature:home"))
    implementation(project(":feature:navigation"))
    implementation(project(":feature:music"))
    implementation(project(":feature:apps"))
    implementation(project(":feature:vehicle"))
    implementation(project(":feature:climate"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:assistant"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.ktx)
}
