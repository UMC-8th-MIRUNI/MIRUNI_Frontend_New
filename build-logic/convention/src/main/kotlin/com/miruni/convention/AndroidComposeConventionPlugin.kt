package com.miruni.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        // compose 활성화
        extensions.configure<CommonExtension<*, *, *, *, *, *>> {
            buildFeatures {
                compose = true
            }
        }

        dependencies {
            add("implementation", platform("androidx.compose:compose-bom:2024.10.00"))
            add("implementation", "androidx.compose.ui:ui")
            add("implementation", "androidx.compose.ui:ui-graphics")
            add("implementation", "androidx.compose.ui:ui-tooling-preview")
            add("debugImplementation", "androidx.compose.ui:ui-tooling")
            add("implementation", "androidx.activity:activity-compose")
            add("androidTestImplementation", platform("androidx.compose:compose-bom:2024.10.00"))
            add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
            add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
        }
    }
}