package com.miruni.convention

import com.miruni.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
            }

            dependencies {
                "implementation"(libs.findLibrary("retrofit").get())
                "implementation"(libs.findLibrary("converter.gson").get())
                "implementation"(libs.findLibrary("okhttp").get())
                "implementation"(libs.findLibrary("okhttp.logging").get())
            }
        }
    }
}