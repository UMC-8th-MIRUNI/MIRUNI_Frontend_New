package com.miruni.feature.signup

import android.util.Patterns
import com.miruni.feature.signup.common.ViewEvent
import com.miruni.feature.signup.common.ViewSideEffect
import com.miruni.feature.signup.common.ViewState
import com.miruni.feature.signup.model.Term
import com.miruni.feature.signup.model.TextInputField
import com.miruni.feature.signup.navigation.SignupRoute


class SignUpContract {

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
        data class OnAgreeRealNameChanged(val agree: Boolean) : Event()
        data class OnAgreeTermsChanged(val agree: Boolean) : Event()
        data class OnAgreePrivacyChanged(val agree: Boolean) : Event()
        data class OnAgreeMarketingChanged(val agree: Boolean) : Event()
        data class OnAgreeAllChanged(val agree: Boolean) : Event()
        data class OnRouteChanged(val route: String) : Event()
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
        val currentRoute: String = SignupRoute.PROFILE,
    ) : ViewState {
        val canNext: Boolean
            get() = when (currentRoute) {
                SignupRoute.Profile.route -> {
                    val emailOk = Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                    val pwOk = password.value.length >= 8
                    val pwMatch =
                        password.value.isNotBlank() && password.value == passwordCheck.value

                    name.value.isNotBlank() &&
                            birth.value.length == 8 && birth.value.all(Char::isDigit) &&
                            phone.value.length in 10..11 && phone.value.all(Char::isDigit)
                            && emailOk && pwOk && pwMatch
                }

                SignupRoute.Terms.route -> {
                    nickName.value.isNotBlank() && agreeTerms && agreePrivacy
                }

                else -> false
            }
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class ToRoute(val route: String) : Navigation()
            data object Back : Navigation()
            data object Done : Navigation()
        }
    }

}