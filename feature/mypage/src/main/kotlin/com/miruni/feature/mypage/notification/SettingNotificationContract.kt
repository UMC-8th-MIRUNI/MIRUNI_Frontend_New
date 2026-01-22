package com.miruni.feature.mypage.notification

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState

class SettingNotificationContract {

    sealed class Event : ViewEvent {
        // 5분 전 알림 스위치
        data class OnReminder5MinChange(val enabled: Boolean) : Event()

        // 10분 전 알림 스위치
        data class OnReminder10MinChange(val enabled: Boolean) : Event()

        // 실행 유도 팝업 알림 스위치
        data class OnExecutionPopupChange(val enabled: Boolean) : Event()

        // 실행 잔소리 알림 스위치
        data class OnExecutionNagChange(val enabled: Boolean) : Event()

        // 마케팅 정보 수신 동의 스위치
        data class OnMarketingConsentChange(val enabled: Boolean) : Event()
    }

    data class State(
        val isReminder5MinEnabled: Boolean = false,
        val isReminder10MinEnabled: Boolean = false,
        val isExecutionPopupEnabled: Boolean = false,
        val isExecutionNagEnabled: Boolean = true,
        val isMarketingConsentEnabled: Boolean = true,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Error(val message: String) : Message()
        }

        data object NotificationSettingSaved : Effect()
    }
}
