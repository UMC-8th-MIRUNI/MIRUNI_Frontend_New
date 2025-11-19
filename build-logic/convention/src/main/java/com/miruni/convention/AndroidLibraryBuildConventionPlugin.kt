package com.miruni.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryBuildConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin = "com.android.library")
                apply(plugin = "org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
//                configureAndroidCommon(this)

                dependencies {
                }

                buildTypes {
//                    release {
//                        isMinifyEnabled = false
//                        proguardFiles(
//                            getDefaultProguardFile("proguard-android-optimize.txt"),
//                            "proguard-rules.pro",
//                        )
//                        signingConfig = signingConfigs.getByName("release.key")
//                    }
//                    debug {
//                        isMinifyEnabled = false
//                        signingConfig = signingConfigs.getByName("debug.key")
//                    }
                }
            }
        }
    }
}