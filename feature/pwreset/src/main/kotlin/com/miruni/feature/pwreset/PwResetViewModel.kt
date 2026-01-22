package com.miruni.feature.pwreset

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.pwreset.domain.usecase.SendEmailVerifyUseCase
import com.miruni.feature.pwreset.presentation.navigation.PwResetRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PwResetViewModel @Inject constructor(
    private val sendEmailVerifyUseCase: SendEmailVerifyUseCase,
) : BaseViewModel<PwResetContract.Event, PwResetContract.State, PwResetContract.Effect>() {
    override fun setInitialState(): PwResetContract.State = PwResetContract.State()


    override fun handleEvents(event: PwResetContract.Event) {
        when(event){
            is PwResetContract.Event.OnEmailChanged -> {
                setState {
                    copy(
                        email = email.copy(value = event.email).clearError()
                    )
                }
            }

            PwResetContract.Event.OnNextClicked -> {
                val state = viewState.value
                if (!state.canNext) return

                val next = when (state.route) {
                    PwResetRoute.Email -> PwResetRoute.Notice
                    PwResetRoute.Notice -> PwResetRoute.Check
                    PwResetRoute.Check -> PwResetRoute.SetPassword
                    PwResetRoute.SetPassword -> PwResetRoute.Success
                    PwResetRoute.Success -> {
                        setEffect { PwResetContract.Effect.Navigation.ToHome }
                        return
                    }
                }
                when (next) {
                    PwResetRoute.Email -> {
                        runNextStep(next) {
                            sendEmailVerifyUseCase(state.email.value)
                        }
                    }

                    PwResetRoute.Notice -> {

                    }
                    PwResetRoute.Check -> {

                    }
                    PwResetRoute.SetPassword -> {

                    }
                    PwResetRoute.Success -> {

                    }
                }

            }
            PwResetContract.Event.OnPrevClicked -> {
                val state = viewState.value
                val prev = when (state.route) {
                    PwResetRoute.Email -> {
                        setEffect { PwResetContract.Effect.Navigation.ToHome }
                        return
                    }
                    PwResetRoute.Notice -> PwResetRoute.Email
                    PwResetRoute.Check -> PwResetRoute.Notice
                    PwResetRoute.SetPassword -> PwResetRoute.Check
                    PwResetRoute.Success -> PwResetRoute.SetPassword
                }
                setState { copy(route = prev) }
                setEffect { PwResetContract.Effect.Navigation.ToRoute(prev) }
            }
            is PwResetContract.Event.OnOtpCodeChanged -> {
                setState {
                    copy(
                        otpCode = otpCode.copy(value = event.otpCode).clearError()
                    )
                }
            }
            is PwResetContract.Event.OnPasswordChanged -> {
                setState {
                    copy(
                        password = password.copy(value = event.password).clearError()
                    )
                }
            }
            is PwResetContract.Event.OnPasswordCheckChanged -> {
                setState {
                    copy(
                        passwordCheck = passwordCheck.copy(value = event.passwordCheck).clearError()
                    )
                }
            }
            PwResetContract.Event.OnTogglePasswordVisible -> {
                setState { copy(passwordVisible = !passwordVisible) }
            }
            PwResetContract.Event.OnTogglePasswordCheckVisible -> {
                setState {
                    copy(passwordCheckVisible = !passwordCheckVisible)
                }
            }
        }
    }

    private fun <T> runNextStep(
        next : PwResetRoute,
        block : suspend () -> DataResult<T, DataError>,
    ){
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = withContext(Dispatchers.IO){
                block()
            }

            when(result){
                is DataResult.Success -> {
                    setState { copy(route = next) }
                    setEffect { PwResetContract.Effect.Navigation.ToRoute(next) }
                }
                is DataResult.Error -> {
                    setEffect { PwResetContract.Effect.Message.Snackbar(result.error.errorMessage) }
                }
            }
            setState { copy(isLoading = false) }
        }
    }

}