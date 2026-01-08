package com.miruni.feature.login

import com.miruni.feature.login.presentation.model.BaseViewModel

class LoginViewModel :
    BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState(): LoginContract.State = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {

            is LoginContract.Event.OnIdChanged -> {
                setState {
                    copy(
                        id = id.copy(value = event.id).clearError()
                    )
                }
            }

            is LoginContract.Event.OnPwChanged -> {
                setState {
                    copy(
                        password = password.copy(value = event.pw).clearError()
                    )
                }
            }

            LoginContract.Event.OnTogglePasswordVisible -> {
                setState { copy(passwordVisible = !passwordVisible) }
            }

            is LoginContract.Event.OnAutoLoginChanged -> {
                setState { copy(autoLogin = event.enabled) }
            }

            LoginContract.Event.OnClearError -> {
                setState {
                    copy(
                        id = id.clearError(),
                        password = password.clearError()
                    )
                }
            }

            LoginContract.Event.OnSignUpClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToSignUp }
            }

            LoginContract.Event.OnResetPasswordClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToResetPassword }
            }

            LoginContract.Event.OnLoginClicked -> {
                if (viewState.value.canLogin) {
                    setEffect { LoginContract.Effect.Navigation.ToNotification }
                }
            }

            LoginContract.Event.OnGoogleLoginClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToNotification }
            }

            LoginContract.Event.OnKakaoLoginClicked -> {
                setEffect { LoginContract.Effect.KakaoLogin }
            }
            is LoginContract.Event.OnKakaoLoginSuccess -> {
                // TODO: 서버에 accessToken 보내서 JWT 교환
                setEffect { LoginContract.Effect.Navigation.ToNotification }
            }

            is LoginContract.Event.OnKakaoLoginFail -> {
                setEffect { LoginContract.Effect.Message.Toast(event.message) }
            }

            LoginContract.Event.OnNotificationClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToStart }
            }

            LoginContract.Event.OnOpenDialog -> {
                setState { copy(isDialogOpen = true) }
            }

            LoginContract.Event.OnCloseDialog -> {
                setState { copy(isDialogOpen = false) }
            }

            LoginContract.Event.OnStartedClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToHome }
            }

        }
    }

}
