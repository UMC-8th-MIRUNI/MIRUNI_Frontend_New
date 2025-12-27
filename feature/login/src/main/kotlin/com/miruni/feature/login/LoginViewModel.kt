package com.miruni.feature.login

import android.util.Patterns
import com.miruni.feature.login.common.BaseViewModel

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

            LoginContract.Event.OnNotificationClicked -> {
                setEffect { LoginContract.Effect.Navigation.ToStart }
            }

            LoginContract.Event.OnOpenDialog -> {
                setState { copy(isDialogOpen = true) }
            }

            LoginContract.Event.OnCloseDialog -> {
                setState { copy(isDialogOpen = false) }
            }

        }
    }

}
