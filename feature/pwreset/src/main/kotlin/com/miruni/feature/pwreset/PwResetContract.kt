package com.miruni.feature.pwreset

import android.util.Patterns
import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.pwreset.presentation.model.TextInputField
import com.miruni.feature.pwreset.presentation.navigation.PwResetRoute

class PwResetContract {
    sealed class Event : ViewEvent {
        data class OnEmailChanged(val email: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnPasswordCheckChanged(val passwordCheck: String) : Event()
        data class OnOtpCodeChanged(val otpCode: String) : Event()
        data object OnTogglePasswordVisible : Event()
        data object OnTogglePasswordCheckVisible : Event()
        data object OnNextClicked : Event()
        data object OnPrevClicked : Event()
    }

    data class State(
        val email: TextInputField = TextInputField(),
        val password: TextInputField = TextInputField(),
        val passwordCheck: TextInputField = TextInputField(),
        val passwordVisible: Boolean = false,
        val passwordCheckVisible: Boolean = false,
        val authCode : String = "",
        val otpCode: TextInputField = TextInputField(),
        val route: PwResetRoute = PwResetRoute.Email,
        val isLoading : Boolean = false,
    ) : ViewState {
        val canNext: Boolean
            get() = when (route) {
                PwResetRoute.Email ->
                    email.value.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email.value)
                        .matches()

                PwResetRoute.Notice -> true
                PwResetRoute.Check -> otpCode.value.length == 4
                PwResetRoute.SetPassword -> {
                    password.value.isNotBlank() &&
                            passwordCheck.value.isNotBlank() &&
                            password.value == passwordCheck.value
                }

                PwResetRoute.Success -> true
            }
    }

    sealed class Effect : ViewSideEffect {
        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Snackbar(
                val message: String,
                val actionLabel: String? = null,
                val onAction: (() -> Unit)? = null
            ) : Message()
        }
        sealed class Navigation : Effect() {
            data object ToHome : Navigation()
            data class ToRoute(val route: PwResetRoute) : Navigation()
        }
    }
}