package com.miruni.feature.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.findProfileImageIndex
import com.miruni.feature.mypage.domain.usecase.GetMyPageInfoUseCase
import com.miruni.feature.mypage.domain.usecase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyPageInfoUseCase: GetMyPageInfoUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {

    init {
        Log.d(TAG, "ViewModel created: $this")
        fetchMyPageInfo()
    }

    override fun setInitialState(): MyPageContract.State = MyPageContract.State()

    override fun handleEvents(event: MyPageContract.Event) {
        Log.d(TAG, "handleEvents() - event: $event")
        when (event) {
            // topbar edit button - 수정 모드 진입
            MyPageContract.Event.OnTopBarEditClick -> {
                Log.d(TAG, "OnTopBarEditClick - Entering edit mode")
                setState {
                    copy(
                        isEditMode = true,
                        // 원본 상태 저장 (취소 시 복원용)
                        originalNickName = nickName,
                        originalProfileImageIndex = selectedProfileImageIndex,
                        errorMessage = null
                    )
                }
            }

            // topbar notification button
            MyPageContract.Event.OnTopBarNotificationClick -> {
                Log.d(TAG, "OnTopBarNotificationClick")
            }

            // 완료 버튼 클릭 - 프로필 업데이트 API 호출
            MyPageContract.Event.OnCompleteClick -> {
                Log.d(TAG, "OnCompleteClick - Updating profile")
                updateProfile()
            }

            // 닉네임 변경
            is MyPageContract.Event.OnNicknameChange -> {
                Log.d(TAG, "OnNicknameChange - new nickname: ${event.nickname}")
                setState {
                    copy(
                        nickName = event.nickname,
                        errorMessage = null
                    )
                }
            }

            // 프로필 이미지 이전으로 넘기기
            MyPageContract.Event.OnProfileImagePrevClick -> {
                val currentState = viewState.value
                val size = currentState.profileImages.size
                val newIndex = (currentState.selectedProfileImageIndex - 1 + size) % size
                Log.d(TAG, "OnProfileImagePrevClick - newIndex: $newIndex")
                setState {
                    copy(
                        selectedProfileImageIndex = newIndex,
                        errorMessage = null
                    )
                }
            }

            // 프로필 이미지 다음으로 넘기기
            MyPageContract.Event.OnProfileImageNextClick -> {
                val currentState = viewState.value
                val size = currentState.profileImages.size
                val newIndex = (currentState.selectedProfileImageIndex + 1) % size
                Log.d(TAG, "OnProfileImageNextClick - newIndex: $newIndex")
                setState {
                    copy(
                        selectedProfileImageIndex = newIndex,
                        errorMessage = null
                    )
                }
            }

            // 계정 설정 클릭
            MyPageContract.Event.OnSettingAccountClick -> {
                Log.d(TAG, "OnSettingAccountClick - Navigate to SettingAccount")
                setEffect { MyPageContract.Effect.Navigation.NavigateToSettingAccount }
            }

            // 알림설정 클릭
            MyPageContract.Event.OnSettingNotificationClick -> {
                Log.d(TAG, "OnSettingNotificationClick - Navigate to SettingNotification")
                setEffect { MyPageContract.Effect.Navigation.NavigateToSettingNotification }
            }

            // 문의 및 정보 클릭
            MyPageContract.Event.OnInfoClick -> {
                Log.d(TAG, "OnInfoClick - Navigate to Info")
                setEffect { MyPageContract.Effect.Navigation.NavigateToInfo }
            }
        }
    }

    /**
     * 프로필 업데이트 API 호출
     */
    private fun updateProfile() {
        val currentState = viewState.value
        val profileImage = currentState.selectedProfileImageApiValue
        val nickname = currentState.nickName

        Log.d(TAG, "updateProfile() - profileImage: $profileImage, nickname: $nickname")

        // 로딩 시작
        setState { copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                updateProfileUseCase(
                    profileImage = profileImage,
                    nickname = nickname
                )
            }

            when (result) {
                is DataResult.Success -> {
                    val userProfile = result.data
                    Log.d(TAG, "updateProfile() Success - userProfile: $userProfile")

                    // 서버 응답으로 UI 업데이트
                    val newProfileImageIndex = findProfileImageIndex(userProfile.profileImage)
                    Log.d(TAG, "updateProfile() - newProfileImageIndex: $newProfileImageIndex")

                    setState {
                        copy(
                            isEditMode = false,
                            isLoading = false,
                            nickName = userProfile.nickname,
                            email = userProfile.email,
                            selectedProfileImageIndex = newProfileImageIndex,
                            originalNickName = userProfile.nickname,
                            originalProfileImageIndex = newProfileImageIndex,
                            errorMessage = null
                        )
                    }

                    setEffect { MyPageContract.Effect.ProfileUpdateSuccess }
                    setEffect { MyPageContract.Effect.Message.Toast("프로필이 업데이트되었습니다.") }
                }

                is DataResult.Error -> {
                    val errorMessage = result.error.errorMessage
                    Log.e(TAG, "updateProfile() Error - errorMessage: $errorMessage")

                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }

                    setEffect { MyPageContract.Effect.Message.Error(errorMessage) }
                }
            }
        }
    }

    /**
     * 마이페이지 사용자 정보 조회 API 호출
     */
    private fun fetchMyPageInfo() {
        Log.d(TAG, "fetchMyPageInfo() called")

        setState { copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getMyPageInfoUseCase()
            }

            when (result) {
                is DataResult.Success -> {
                    val userProfile = result.data
                    Log.d(TAG, "fetchMyPageInfo() Success - userProfile: $userProfile")

                    val profileImageIndex = findProfileImageIndex(userProfile.profileImage)
                    Log.d(TAG, "fetchMyPageInfo() - profileImageIndex: $profileImageIndex")

                    setState {
                        copy(
                            isLoading = false,
                            nickName = userProfile.nickname,
                            email = userProfile.email,
                            selectedProfileImageIndex = profileImageIndex,
                            originalNickName = userProfile.nickname,
                            originalProfileImageIndex = profileImageIndex,
                            errorMessage = null
                        )
                    }
                }

                is DataResult.Error -> {
                    val errorMessage = result.error.errorMessage
                    Log.e(TAG, "fetchMyPageInfo() Error - errorMessage: $errorMessage")

                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MyPageViewModel"
    }
}
