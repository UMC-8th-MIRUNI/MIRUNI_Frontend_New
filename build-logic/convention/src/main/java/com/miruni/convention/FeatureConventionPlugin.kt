package com.miruni.convention

import com.miruni.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("convention.hilt")
//                apply("convention.ui.compose")
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:common"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.android").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.material3").get())

                add("testImplementation", libs.findLibrary("androidx.navigation.testing").get())
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx.lifecycle.runtimeTesting").get()
                )
            }
        }
    }
}