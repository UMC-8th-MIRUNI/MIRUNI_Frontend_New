package com.miruni.convention

import com.miruni.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class UiComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {

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