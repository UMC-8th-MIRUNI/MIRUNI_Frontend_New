package com.miruni.feature.mypage

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.mypage.domain.ProfileImage


class MyPageContract {

    sealed class Event : ViewEvent {
        
        // top bar 영역
        object OnTopBarEditClick : Event() // 수정
        object OnTopBarNotificationClick : Event() // 알림 클릭

        // 프로필 영역
        object OnEditProfileClick : Event()
        object OnCompleteClick : Event()

        // 닉네임
        data class OnNicknameChange(val nickname: String) : Event()

        // 프로필 이미지
        object OnProfileImagePrevClick : Event()
        object OnProfileImageNextClick : Event()

        // 설정
        object OnSettingAccountClick : Event() // 계정 설정
        object OnSettingNotificationClick : Event() // 알림 설정
        object OnInfoClick : Event() // 문의 및 정보
    }

    data class State(
        val isEditMode: Boolean = false,

        val nickName: String = "닉네임",

        val profileImages: List<ProfileImage> = dummyProfileImage(),
        val selectedProfileImageIndex: Int = 0,

        val isCompleteEnabled: Boolean = false
    ) : ViewState


    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        object NavigateToSettingAccount : Effect()
        object NavigateToSettingNotification : Effect()
        object NavigateToInfo : Effect()
        }

//        object ShowKeyboard : Effect()
//        object HideKeyboard : Effect()
    }
}


