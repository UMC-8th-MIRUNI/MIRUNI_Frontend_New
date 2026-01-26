package com.miruni.feature.mypage.info.feedback

import android.util.Log
import com.miruni.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    // TODO: Add FeedbackUseCase when API is ready
) : BaseViewModel<FeedbackContract.Event, FeedbackContract.State, FeedbackContract.Effect>() {

    override fun setInitialState(): FeedbackContract.State = FeedbackContract.State()

    override fun handleEvents(event: FeedbackContract.Event) {
        Log.d(TAG, "handleEvents() - event: $event")
        when (event) {
            // 제목 변경
            is FeedbackContract.Event.OnTitleChange -> {
                Log.d(TAG, "OnTitleChange - new title: ${event.title}")
                setState {
                    copy(
                        title = event.title,
                        errorMessage = null
                    )
                }
            }

            // 내용 변경
            is FeedbackContract.Event.OnContentChange -> {
                Log.d(TAG, "OnContentChange - new content: ${event.content}")
                setState {
                    copy(
                        content = event.content,
                        errorMessage = null
                    )
                }
            }

            // 개인정보 수집 동의 변경
            is FeedbackContract.Event.OnPrivacyConsentChange -> {
                Log.d(TAG, "OnPrivacyConsentChange - isChecked: ${event.isChecked}")
                setState {
                    copy(
                        isPrivacyConsentChecked = event.isChecked,
                        errorMessage = null
                    )
                }
            }

            // 사진 선택
            is FeedbackContract.Event.OnPhotosSelected -> {
                Log.d(TAG, "OnPhotosSelected - photos count: ${event.photos.size}")
                setState {
                    copy(
                        selectedPhotos = event.photos,
                        errorMessage = null
                    )
                }
            }

            // 제출 버튼 클릭
            FeedbackContract.Event.OnSubmitClick -> {
                Log.d(TAG, "OnSubmitClick - submitting feedback")
                submitFeedback()
            }

            // 뒤로가기
            FeedbackContract.Event.OnBackClick -> {
                Log.d(TAG, "OnBackClick")
                setEffect { FeedbackContract.Effect.Navigation.NavigateBack }
            }
        }
    }

    /**
     * 피드백 제출
     * TODO: API 연동 시 서버에 데이터 전송
     */
    private fun submitFeedback() {
        val currentState = viewState.value

        Log.d(TAG, "submitFeedback() - title: ${currentState.title}, " +
                "content: ${currentState.content}, " +
                "privacyConsent: ${currentState.isPrivacyConsentChecked}, " +
                "photos: ${currentState.selectedPhotos.size}")

        // 로딩 시작
        setState { copy(isLoading = true, errorMessage = null) }

        // TODO: API 호출 구현
        // 현재는 바로 성공 처리하고 SubmitFeedbackScreen으로 이동
        setState { copy(isLoading = false) }
        setEffect { FeedbackContract.Effect.Navigation.NavigateToSubmitFeedback }
    }

    /**
     * 상태 초기화 (제출 완료 후 호출)
     */
    fun resetState() {
        Log.d(TAG, "resetState()")
        setState { FeedbackContract.State() }
    }

    companion object {
        private const val TAG = "FeedbackViewModel"
    }
}
