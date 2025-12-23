package com.miruni.feature.login

import com.miruni.feature.login.common.ViewEvent
import com.miruni.feature.login.common.ViewSideEffect
import com.miruni.feature.login.common.ViewState
import com.miruni.feature.login.model.TextInputField

class LoginContract {

    sealed class Event : ViewEvent {
        data class OnIdChanged(val id: String) : Event()
        data class OnPwChanged(val pw: String) : Event()
        data object OnTogglePasswordVisible : Event()

        data class OnAutoLoginChanged(val enabled: Boolean) : Event()

        data object OnLoginClicked : Event()
        data object OnSignUpClicked : Event()
        data object OnResetPasswordClicked : Event()

        data object OnClearError : Event()
    }

    data class State(
        val id: TextInputField = TextInputField(),
        val password: TextInputField = TextInputField(),
        val passwordVisible: Boolean = false,
        val autoLogin: Boolean = false,
        val isLoading: Boolean = false,
    ) : ViewState {
        val canLogin: Boolean
            get() = id.value.isNotBlank() && password.value.isNotBlank() && !isLoading
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToHome : Navigation()
            data object ToSignUp : Navigation()
            data object ToResetPassword : Navigation()
        }

        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
        }
    }
}
