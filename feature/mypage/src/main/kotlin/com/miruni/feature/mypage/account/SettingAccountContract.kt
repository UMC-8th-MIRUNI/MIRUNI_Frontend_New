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
        val isLoading: Boolean = false,

        // 현재 값
        val name: String = "",
        val birth: String = "",
        val phoneNumber: String = "",
        val email: String = "",

        // 원본 값 (취소 시 복원용)
        val originalName: String = "",
        val originalBirth: String = "",
        val originalPhoneNumber: String = "",

        // 에러 메시지
        val errorMessage: String? = null
    ) : ViewState {

        /**
         * 완료 버튼 활성화 여부
         * 이름, 생년월일, 전화번호 중 하나라도 변경되었을 때 활성화
         */
        val isCompleteEnabled: Boolean
            get() = isEditMode && !isLoading && (
                name != originalName ||
                birth != originalBirth ||
                phoneNumber != originalPhoneNumber
            )
    }

    sealed class Effect : ViewSideEffect {
        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Error(val message: String) : Message()
        }

        object AccountUpdateSuccess : Effect()
    }
}
