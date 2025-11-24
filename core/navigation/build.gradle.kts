plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.convention.ui.compose)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.miruni.core.navigation"
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
    implementation(project(":core:designsystem"))
    implementation(project(":core:auth"))
    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))
    implementation(project(":data:dto"))
//    implementation(project(":feature:home"))
//    implementation(project(":feature:splash"))
//    implementation(project(":feature:aiplanner"))
//    implementation(project(":feature:calendar"))
//    implementation(project(":feature:home"))
//    implementation(project(":feature:login"))
//    implementation(project(":feature:mypage"))
//    implementation(project(":feature:onboard"))
//    implementation(project(":feature:pwreset"))
//    implementation(project(":feature:signup"))
//    implementation(project(":feature:splash"))
//    implementation(project(":feature:survey"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}