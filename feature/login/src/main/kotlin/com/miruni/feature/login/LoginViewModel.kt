package com.miruni.feature.login

import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.domain.fcm.RegisterFcmTokenUseCase
import com.miruni.core.result.DataResult
import com.miruni.feature.login.domain.usecase.GetGoogleLoginUseCase
import com.miruni.feature.login.domain.usecase.GetKakaoLoginUseCase
import com.miruni.feature.login.domain.usecase.GetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val getGoogleLoginUseCase: GetGoogleLoginUseCase,
    private val getKakaoLoginUseCase: GetKakaoLoginUseCase,
    private val registerFcmTokenUseCase: RegisterFcmTokenUseCase
) :
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
//                setEffect { LoginContract.Effect.Navigation.ToHome } // 임시 로그인
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        getLoginUseCase(
                            id = viewState.value.id.value,
                            password = viewState.value.password.value,
                            autoLogin = viewState.value.autoLogin
                        )
                    }
                    when (result) {
                        is DataResult.Success -> {
                            val token = result.data
                            val fcmToken = withContext(Dispatchers.IO) {
                                registerFcmTokenUseCase(token.accessToken)
                            }
                            when (fcmToken) {
                                is DataResult.Success -> {
                                    setEffect { LoginContract.Effect.Navigation.ToHome }
                                }
                                is DataResult.Error -> {
                                    setEffect { LoginContract.Effect.Message.Snackbar(fcmToken.error.errorMessage) }
                                }
                            }

                        }

                        is DataResult.Error -> {
                            setState {
                                copy(
                                    password = password.copy(
                                        isError = true,
                                        errorMessage = result.error.errorMessage
                                    ),
                                )
                            }
                        }
                    }
                }
            }

            LoginContract.Event.OnGoogleLoginClicked -> {
                setEffect { LoginContract.Effect.GoogleLogin }
            }

            is LoginContract.Event.OnGoogleLoginSuccess -> {
                setEffect { LoginContract.Effect.Navigation.ToNotification }
            }

            is LoginContract.Event.OnGoogleLoginFail -> {
                setEffect { LoginContract.Effect.Message.Snackbar(event.message) }
            }
            LoginContract.Event.OnKakaoLoginClicked -> {
                setEffect { LoginContract.Effect.KakaoLogin }
            }

            is LoginContract.Event.OnKakaoLoginSuccess -> {
                // TODO: 서버에 accessToken 보내서 JWT 교환
                setEffect { LoginContract.Effect.Navigation.ToNotification }
            }

            is LoginContract.Event.OnKakaoLoginFail -> {
                setEffect { LoginContract.Effect.Message.Snackbar(event.message) }
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
