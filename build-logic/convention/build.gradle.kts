plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
//    alias(libs.plugins.convention.ui.compose)
    `kotlin-dsl`
}
group = "com.miruni.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.com.google.devtools.ksp.gradle.plugin)
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("hilt") {
            id = libs.plugins.convention.hilt.get().pluginId
            implementationClass = "com.miruni.convention.HiltConventionPlugin"
        }
    }
    plugins {
        register("retrofit") {
            id = libs.plugins.convention.retrofit.get().pluginId
            implementationClass = "com.miruni.convention.RetrofitConventionPlugin"
        }
    }
    plugins {
        register("uiCompose") {
            id = "convention.ui.compose"
//            id = libs.plugins.convention.ui.compose.get().pluginId
            implementationClass = "com.miruni.convention.UiComposeConventionPlugin"
        }
    }
    plugins {
        register("androidLibraryBuild") {
            id = libs.plugins.convention.android.lib.build.get().pluginId
            implementationClass = "com.miruni.convention.AndroidLibraryBuildConventionPlugin"
        }
    }
    plugins {
        register("feature") {
            id = libs.plugins.convention.feature.get().pluginId
            implementationClass = "com.miruni.convention.FeatureConventionPlugin"
        }
    }
}