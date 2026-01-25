package com.miruni.feature.signup

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.domain.model.User
import com.miruni.feature.signup.domain.usecase.SendEmailVerifyUseCase
import com.miruni.feature.signup.domain.usecase.SignupUseCase
import com.miruni.feature.signup.domain.usecase.VerifyEmailUseCase
import com.miruni.feature.signup.presentation.navigation.SignupRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val sendEmailVerifyUseCase: SendEmailVerifyUseCase,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val signupUseCase: SignupUseCase
) : BaseViewModel<SignUpContract.Event, SignUpContract.State, SignUpContract.Effect>() {

    private val routes = SignupRoute.sequence
    override fun setInitialState(): SignUpContract.State = SignUpContract.State()

    override fun handleEvents(event: SignUpContract.Event) {
        when (event) {

            is SignUpContract.Event.OnNameChanged -> {
                setState {
                    copy(
                        name = name.copy(
                            value = event.name
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnNickNameChanged -> {
                setState {
                    copy(
                        nickName = nickName.copy(
                            value = event.nickName
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnBirthChanged -> {
                val digits = event.birth.filter { it.isDigit() }.take(8)
                setState {
                    copy(
                        birth = birth.copy(
                            value = digits
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnPhoneChanged -> {
                val digits = event.phone.filter { it.isDigit() }.take(11)
                setState {
                    copy(
                        phone = phone.copy(
                            value = digits
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnEmailChanged -> {
                setState {
                    copy(
                        email = email.copy(
                            value = event.email
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnOtpChanged -> {
                setState {
                    copy(
                        otp = otp.copy(
                            value = event.otp
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnPasswordChanged -> {
                val pw = event.password
                val ok = pw.length >= 8 && pw.any(Char::isLetter) && pw.any(Char::isDigit)

                setState {
                    copy(
                        password = if (ok) {
                            password.copy(value = pw).clearError()
                        } else {
                            password.copy(value = pw).withError("영문, 숫자를 포함해서 8자 이상으로 설정해주세요.")
                        }
                    )
                }
            }


            is SignUpContract.Event.OnPasswordCheckChanged -> {
                setState {
                    copy(
                        passwordCheck = passwordCheck.copy(
                            value = event.passwordCheck
                        ).clearError()
                    )
                }
            }

            is SignUpContract.Event.OnSelectedTermChanged -> {
                setState { copy(selectedTerm = event.term) }
            }


            is SignUpContract.Event.OnAgreeRealNameChanged -> {
                setState { copy(agreeRealName = event.agree) }
            }

            is SignUpContract.Event.OnAgreeTermsChanged -> {
                setState { copy(agreeTerms = event.agree) }
            }

            is SignUpContract.Event.OnAgreePrivacyChanged -> {
                setState { copy(agreePrivacy = event.agree) }
            }

            is SignUpContract.Event.OnAgreeMarketingChanged -> {
                setState { copy(agreeMarketing = event.agree) }
            }

            is SignUpContract.Event.OnAgreeAllChanged -> {
                setState {
                    copy(
                        agreeTerms = event.agree,
                        agreePrivacy = event.agree,
                        agreeMarketing = event.agree,
                    )
                }
            }

            is SignUpContract.Event.OnRouteChanged -> {
                setState { copy(currentRoute = event.route) }
            }

            is SignUpContract.Event.OnRequestOtpClicked -> {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO){
                        sendEmailVerifyUseCase(viewState.value.email.value)
                    }
                    when(result){
                        is DataResult.Success -> {
                            setState {
                                copy(
                                    isEmailVerified = true
                                )
                            }
                        }
                        is DataResult.Error -> {
                            setState {
                                copy(
                                    otp = otp.copy(
                                        value = "",
                                        isError = true,
                                        errorMessage = result.error.errorMessage
                                    )
                                )
                            }
                        }
                    }
                }
            }

            is SignUpContract.Event.OnVerifyOtpClicked -> {
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO){
                        verifyEmailUseCase(viewState.value.otp.value)
                    }
                    when(result) {
                        is DataResult.Success -> {
                            setState {
                                copy(
                                    isOtpVerified = true
                                )
                            }
                        }
                        is DataResult.Error -> {
                            setState {
                                copy(
                                    otp = otp.copy(
                                        value = "",
                                        isError = true,
                                        errorMessage = result.error.errorMessage
                                    )
                                )
                            }
                        }
                    }
                }
            }

            SignUpContract.Event.OnNextStepClicked -> {
                val current = viewState.value.currentRoute
                val idx = routes.indexOf(current).coerceAtLeast(0)

                if (idx >= routes.lastIndex) {
                    setEffect { SignUpContract.Effect.Navigation.Done }
                } else {
                    val next = routes[idx + 1]
                    if (next == SignupRoute.TERMS) {
                        viewModelScope.launch {
                            val result = withContext(Dispatchers.IO){
                                signupUseCase(
                                    User(
                                        name = viewState.value.name.value,
                                        email = viewState.value.email.value,
                                        phoneNumber = viewState.value.phone.value,
                                        password = viewState.value.password.value,
                                        nickname = viewState.value.nickName.value,
                                        birthday = viewState.value.birth.value
                                    )
                                )
                            }
                            when(result){
                                is DataResult.Success -> {
                                    setEffect { SignUpContract.Effect.Navigation.Done }
                                }
                                is DataResult.Error -> {
                                    setEffect {
                                        SignUpContract.Effect.Message.SnackBar(result.error.errorMessage)
                                    }
                                }
                            }
                        }
                    } else {
                        setEffect { SignUpContract.Effect.Navigation.ToRoute(next) }
                    }
                }
            }

            SignUpContract.Event.OnPrevStepClicked -> {
                val current = viewState.value.currentRoute
                val idx = routes.indexOf(current).coerceAtLeast(0)

                if (idx <= 0) {
                    setEffect { SignUpContract.Effect.Navigation.Back }
                } else {
                    val prev = routes[idx - 1]
                    setEffect { SignUpContract.Effect.Navigation.ToRoute(prev) }
                }
            }

        }
    }
}