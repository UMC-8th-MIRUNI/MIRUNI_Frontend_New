package com.miruni.feature.pwreset.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class PwResetRoute(val route : String) {
    data object Email : PwResetRoute("email")
    data object Notice : PwResetRoute("notice")
    data object Check : PwResetRoute("check")
    data object SetPassword : PwResetRoute("reset")
    data object Success : PwResetRoute("success")
}