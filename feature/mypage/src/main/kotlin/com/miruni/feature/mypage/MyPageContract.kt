package com.miruni.feature.mypage

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.mypage.domain.ProfileImage
import com.miruni.feature.mypage.domain.getProfileImages


class MyPageContract {

    sealed class Event : ViewEvent {

        // top bar 영역
        object OnTopBarEditClick : Event() // 수정
        object OnTopBarNotificationClick : Event() // 알림 클릭

        // 프로필 영역
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

    // 임시. 프로덕션 진행 시 초기화
    data class State(
        val isEditMode: Boolean = false, // 수정 모드
        val nickName: String = "김가영", // 닉네임
        val email: String = "gayeong@gmail.com", // 이메일
        val profileImages: List<ProfileImage> = getProfileImages(), // 프로필 이미지 목록
        val selectedProfileImageIndex: Int = 0, // 현재 선택된 프로필 이미지 인덱스

        // 수정 전 원본 상태 (취소 시 복원용)
        val originalNickName: String = "김가영",
        val originalProfileImageIndex: Int = 0,

        // API 요청 상태
        val isLoading: Boolean = false, // 로딩 중
        val errorMessage: String? = null, // 에러 메시지
    ) : ViewState {

        /**
         * 완료 버튼 활성화 여부
         * 닉네임 또는 프로필 이미지가 변경되었을 때만 활성화
         */
        val isCompleteEnabled: Boolean
            get() = isEditMode && !isLoading && (
                nickName != originalNickName ||
                selectedProfileImageIndex != originalProfileImageIndex
            )

        /**
         * 현재 선택된 프로필 이미지의 API 값
         */
        val selectedProfileImageApiValue: String
            get() = profileImages.getOrNull(selectedProfileImageIndex)?.apiValue ?: "BETTY"
    }


    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateToSettingAccount : Navigation()
            object NavigateToSettingNotification : Navigation()
            object NavigateToInfo : Navigation()
        }

        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Error(val message: String) : Message()
        }

        object ProfileUpdateSuccess : Effect()
    }
}
