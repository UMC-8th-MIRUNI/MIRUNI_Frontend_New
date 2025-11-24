plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.retrofit)
    alias(libs.plugins.convention.android.lib.build)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.miruni.core.auth"
    compileSdk = 35

    buildFeatures {
        compose = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // module 의존성
    implementation(project(":domain:model"))
    implementation(project(":domain:usecase"))
    implementation(project(":domain:repository"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}