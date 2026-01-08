package com.miruni.feature.signup.navigation

sealed class SignupRoute(val route: String) {

    data object Profile : SignupRoute(PROFILE)
    data object Terms : SignupRoute(TERMS)

    companion object {
        const val PROFILE = "signup/profile"
        const val TERMS = "signup/terms"

        val sequence = listOf(PROFILE, TERMS)
    }
}
