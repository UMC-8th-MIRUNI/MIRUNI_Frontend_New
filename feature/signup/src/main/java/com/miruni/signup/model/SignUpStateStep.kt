package com.miruni.signup.model

sealed class SignUpStateStep() {
    data object Profile : SignUpStateStep()
    data object Account : SignUpStateStep()

    data object Terms : SignUpStateStep()
}