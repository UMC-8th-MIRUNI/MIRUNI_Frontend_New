package com.miruni.feature.signup

import com.miruni.feature.signup.common.ViewEvent
import com.miruni.feature.signup.common.ViewSideEffect
import com.miruni.feature.signup.common.ViewState
import com.miruni.feature.signup.model.TextInputField
import com.miruni.feature.signup.model.SignupStateStep
import com.miruni.feature.signup.model.Term


class SignUpContract{

    companion object {
        val stepSequence: List<SignupStateStep> = listOf(
            SignupStateStep.Profile,
            SignupStateStep.Account,
            SignupStateStep.Terms,
        )
    }

    sealed class Event : ViewEvent {
        data class OnNameChanged(val name: String) : Event()
        data class OnNickNameChanged(val nickName: String) : Event()
        data class OnBirthChanged(val birth: String) : Event()
        data class OnPhoneChanged(val phone: String) : Event()
        data class OnEmailChanged(val email: String) : Event()
        data class OnOtpChanged(val otp: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data class OnPasswordCheckChanged(val passwordCheck: String) : Event()
        data class OnSelectedTermChanged(val term: Term?) : Event()
        data class OnStepChanged(val step: SignupStateStep) : Event()
        data class OnAgreeRealNameChanged(val agree: Boolean) : Event()
        data class OnAgreeTermsChanged(val agree: Boolean) : Event()
        data class OnAgreePrivacyChanged(val agree: Boolean) : Event()
        data class OnAgreeMarketingChanged(val agree: Boolean) : Event()
        data class OnAgreeAllChanged(val agree: Boolean) : Event()
        data object OnNextStepClicked : Event()
        data object OnPrevStepClicked : Event()

    }

    data class State(
        val name: TextInputField = TextInputField(),
        val nickName: TextInputField = TextInputField(),
        val birth: TextInputField = TextInputField(),
        val phone: TextInputField = TextInputField(),
        val email: TextInputField = TextInputField(),
        val otp: TextInputField = TextInputField(),
        val password: TextInputField = TextInputField(),
        val passwordCheck: TextInputField = TextInputField(),
        val openTerm: Term? = null,
        val selectedTerm: Term? = null,
        val agreeRealName: Boolean = false,
        val agreeTerms: Boolean = false,
        val agreePrivacy: Boolean = false,
        val agreeMarketing: Boolean = false,
        val step: SignupStateStep = SignupStateStep.Profile,
        ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToHome : Navigation()
        }
    }
}