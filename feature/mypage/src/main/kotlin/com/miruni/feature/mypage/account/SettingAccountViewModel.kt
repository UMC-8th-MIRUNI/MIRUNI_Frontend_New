package com.miruni.feature.mypage.account

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.usecase.GetMyPageInfoUseCase
import com.miruni.feature.mypage.domain.usecase.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingAccountViewModel @Inject constructor(
    private val getMyPageInfoUseCase: GetMyPageInfoUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : BaseViewModel<SettingAccountContract.Event, SettingAccountContract.State, SettingAccountContract.Effect>() {

    init {
        Log.d(TAG, "ViewModel created: $this")
        fetchMyPageInfo()
    }

    override fun setInitialState(): SettingAccountContract.State = SettingAccountContract.State()

    override fun handleEvents(event: SettingAccountContract.Event) {
        Log.d(TAG, "handleEvents() - event: $event")
        when (event) {
            // 수정 모드 진입
            SettingAccountContract.Event.OnEditClick -> {
                Log.d(TAG, "OnEditClick - Entering edit mode")
                val currentState = viewState.value
                setState {
                    copy(
                        isEditMode = true,
                        originalName = currentState.name,
                        originalBirth = currentState.birth,
                        originalPhoneNumber = currentState.phoneNumber,
                        errorMessage = null
                    )
                }
            }

            // 완료 버튼 클릭 - 계정 정보 업데이트 API 호출
            SettingAccountContract.Event.OnCompleteClick -> {
                Log.d(TAG, "OnCompleteClick - Updating account")
                updateAccount()
            }

            // 이름 변경
            is SettingAccountContract.Event.OnNameChange -> {
                Log.d(TAG, "OnNameChange - new name: ${event.value}")
                setState {
                    copy(
                        name = event.value,
                        errorMessage = null
                    )
                }
            }

            // 생년월일 변경
            is SettingAccountContract.Event.OnBirthChange -> {
                Log.d(TAG, "OnBirthChange - new birth: ${event.value}")
                setState {
                    copy(
                        birth = event.value,
                        errorMessage = null
                    )
                }
            }

            // 전화번호 변경
            is SettingAccountContract.Event.OnPhoneChange -> {
                Log.d(TAG, "OnPhoneChange - new phoneNumber: ${event.value}")
                setState {
                    copy(
                        phoneNumber = event.value,
                        errorMessage = null
                    )
                }
            }
        }
    }

    /**
     * 계정 정보 업데이트 API 호출
     */
    private fun updateAccount() {
        val currentState = viewState.value
        val name = currentState.name.ifBlank { null }
        val birth = currentState.birth.ifBlank { null }
        val phoneNumber = currentState.phoneNumber.ifBlank { null }

        Log.d(TAG, "updateAccount() - name: $name, birth: $birth, phoneNumber: $phoneNumber")

        // 로딩 시작
        setState { copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                updateAccountUseCase(
                    name = name,
                    birth = birth,
                    phoneNumber = phoneNumber
                )
            }

            when (result) {
                is DataResult.Success -> {
                    val userAccount = result.data
                    Log.d(TAG, "updateAccount() Success - userAccount: $userAccount")

                    // 서버 응답으로 UI 업데이트
                    setState {
                        copy(
                            isEditMode = false,
                            isLoading = false,
                            name = userAccount.name ?: "",
                            birth = userAccount.birth ?: "",
                            phoneNumber = userAccount.phoneNumber ?: "",
                            email = userAccount.email,
                            originalName = userAccount.name ?: "",
                            originalBirth = userAccount.birth ?: "",
                            originalPhoneNumber = userAccount.phoneNumber ?: "",
                            errorMessage = null
                        )
                    }

                    setEffect { SettingAccountContract.Effect.AccountUpdateSuccess }
                    setEffect { SettingAccountContract.Effect.Message.Toast("계정 정보가 업데이트되었습니다.") }
                }

                is DataResult.Error -> {
                    val errorMessage = result.error.errorMessage
                    Log.e(TAG, "updateAccount() Error - errorMessage: $errorMessage")

                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }

                    setEffect { SettingAccountContract.Effect.Message.Error(errorMessage) }
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

                    setState {
                        copy(
                            isLoading = false,
                            name = userProfile.name ?: "",
                            birth = userProfile.birth ?: "",
                            phoneNumber = userProfile.phoneNumber ?: "",
                            email = userProfile.email,
                            originalName = userProfile.name ?: "",
                            originalBirth = userProfile.birth ?: "",
                            originalPhoneNumber = userProfile.phoneNumber ?: "",
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
        private const val TAG = "SettingAccountViewModel"
    }
}
