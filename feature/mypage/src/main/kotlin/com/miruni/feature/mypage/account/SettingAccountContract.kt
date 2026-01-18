package com.miruni.feature.mypage.account

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState

class SettingAccountContract {

    sealed class Event : ViewEvent {
        object OnEditClick : Event()
        object OnCompleteClick : Event()

        data class OnNameChange(val value: String) : Event()
        data class OnBirthChange(val value: String) : Event()
        data class OnPhoneChange(val value: String) : Event()
    }

    data class State(
        val isEditMode: Boolean = false,
        val isSaving: Boolean = false,

        val name: String = "",
        val birth: String = "",
        val phoneNumber: String = "",
        val email: String = ""
    ) : ViewState

    sealed class Effect : ViewSideEffect {

    }
}