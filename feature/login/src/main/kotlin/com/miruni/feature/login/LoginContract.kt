package com.miruni.feature.login

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.login.presentation.model.TextInputField

class LoginContract {

    sealed class Event : ViewEvent {
        data class OnIdChanged(val id: String) : Event()
        data class OnPwChanged(val pw: String) : Event()
        data object OnTogglePasswordVisible : Event()

        data class OnAutoLoginChanged(val enabled: Boolean) : Event()

        data object OnLoginClicked : Event()
        data object OnSignUpClicked : Event()
        data object OnResetPasswordClicked : Event()

        data object OnGoogleLoginClicked : Event()
        data class OnGoogleLoginSuccess(val accessToken: String) : Event()
        data class OnGoogleLoginFail(val message: String) : Event()

        data object OnKakaoLoginClicked : Event()
        data class OnKakaoLoginSuccess(val accessToken: String) : Event()
        data class OnKakaoLoginFail(val message: String) : Event()

        data object OnNotificationClicked : Event()
        data object OnStartedClicked : Event()
        data object OnClearError : Event()
        data object OnOpenDialog : Event()
        data object OnCloseDialog : Event()
    }

    data class State(
        val id: TextInputField = TextInputField(""),
        val password: TextInputField = TextInputField(""),
        val passwordVisible: Boolean = false,
        val autoLogin: Boolean = false,
        val isLoading: Boolean = false,
        val isDialogOpen: Boolean = false,
    ) : ViewState {
        val canLogin: Boolean
            // get() = id.value.isNotBlank() && password.value.isNotBlank() && !isLoading
            get() = true
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToHome : Navigation()
            data object ToSignUp : Navigation()
            data object ToResetPassword : Navigation()

            data object ToNotification : Navigation()
            data object ToStart : Navigation()
        }

        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Snackbar(
                val message: String,
                val actionLabel: String? = null,
                val onAction: (() -> Unit)? = null
            ) : Message()
        }
        data object KakaoLogin : Effect()
        data object GoogleLogin : Effect()
    }
}
