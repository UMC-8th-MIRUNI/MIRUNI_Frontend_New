package com.miruni.convention

import com.miruni.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin = "com.google.devtools.ksp")
                apply(plugin = "dagger.hilt.android.plugin")
            }

            dependencies {
                "ksp"(libs.findLibrary("hilt.compiler").get())
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
            }
        }
    }
}