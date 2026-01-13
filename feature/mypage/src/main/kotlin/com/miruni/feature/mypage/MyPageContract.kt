package com.miruni.feature.mypage

import androidx.annotation.DrawableRes
import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState


class MyPageContract {
    data class ProfileImage(
        @DrawableRes val resId: Int
    )

    sealed class Event : ViewEvent {
        object OnTopBarEditClick : Event() // 수정
        object OnTopBarNotificationClick : Event() // 알림 클릭

        object OnSettingAccountClick : Event() // 계정 설정
        object OnSettingNotificationClick : Event() // 알림 설정
        object OnInfoClick : Event() // 문의 및 정보
    }

    data class State(
        val selectedProfileImage: ProfileImage,
        val isEditingProfile: Boolean
    ) : ViewState


    sealed class Effect : ViewSideEffect {
        object NavigateToSettingAccount : Effect()
        object NavigateToSettingNotification : Effect()
        object NavigateToInfo : Effect()
    }
}