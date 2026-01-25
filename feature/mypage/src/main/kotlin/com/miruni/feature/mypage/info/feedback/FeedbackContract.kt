package com.miruni.feature.mypage.info.feedback

import android.net.Uri
import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState

class FeedbackContract {

    sealed class Event : ViewEvent {
        // WriteFeedbackScreen events
        data class OnTitleChange(val title: String) : Event()
        data class OnContentChange(val content: String) : Event()
        data class OnPrivacyConsentChange(val isChecked: Boolean) : Event()
        data class OnPhotosSelected(val photos: List<Uri>) : Event()
        object OnSubmitClick : Event()

        // Navigation events
        object OnBackClick : Event()
    }

    data class State(
        val title: String = "",
        val content: String = "",
        val isPrivacyConsentChecked: Boolean = false,
        val selectedPhotos: List<Uri> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) : ViewState {

        /**
         * 제출 버튼 활성화 여부
         * 제목, 내용, 개인정보 수집 동의가 모두 입력/체크되었을 때만 활성화
         * 사진 선택은 버튼 활성화에 영향을 주지 않음
         */
        val isSubmitEnabled: Boolean
            get() = title.isNotBlank() && content.isNotBlank() && isPrivacyConsentChecked && !isLoading
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object NavigateToSubmitFeedback : Navigation()
            object NavigateToInformation : Navigation()
            object NavigateBack : Navigation()
        }

        sealed class Message : Effect() {
            data class Toast(val message: String) : Message()
            data class Error(val message: String) : Message()
        }
    }
}
