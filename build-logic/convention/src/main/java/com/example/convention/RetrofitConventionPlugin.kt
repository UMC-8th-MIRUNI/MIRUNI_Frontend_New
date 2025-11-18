package com.example.convention

import com.example.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            dependencies {
                "implementation"(libs.findLibrary("retrofit").get())
                "implementation"(libs.findLibrary("converter.gson").get())
            }
        }
    }
}