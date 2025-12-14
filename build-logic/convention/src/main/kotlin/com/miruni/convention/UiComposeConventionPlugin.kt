package com.miruni.convention

import com.android.build.api.dsl.CommonExtension
import com.miruni.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class UiComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Android 모듈 체크
//            if (plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application")) {
//                extensions.configure<CommonExtension<*, *, *, *, *, *>> {
//                    buildFeatures { compose = true }
//                }
//
//                pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
//            }
            with(pluginManager) {
                // compose 활성화
                extensions.configure<CommonExtension<*, *, *, *, *, *>> {
                    buildFeatures {
                        compose = true
                    }
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("androidx.activity.compose").get())
                "implementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
                "implementation"(libs.findLibrary("androidx.ui").get())
                "implementation"(libs.findLibrary("androidx.material3").get())
                "implementation"(libs.findLibrary("androidx.ui.graphics").get())

                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}