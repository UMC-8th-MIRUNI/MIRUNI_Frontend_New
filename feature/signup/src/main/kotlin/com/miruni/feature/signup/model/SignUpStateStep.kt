package com.miruni.feature.signup.model

sealed class SignupStateStep() {
    data object Profile : SignupStateStep()
    data object Account : SignupStateStep()

    data object Terms : SignupStateStep()
}