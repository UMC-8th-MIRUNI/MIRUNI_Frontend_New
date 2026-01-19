package com.miruni.feature.mypage.account

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.result.DataResult
import com.miruni.feature.mypage.domain.usecase.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingAccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase
) : BaseViewModel<SettingAccountContract.Event, SettingAccountContract.State, SettingAccountContract.Effect>() {

    init {
        Log.d(TAG, "ViewModel created: $this")
    }

    override fun setInitialState(): SettingAccountContract.State = SettingAccountContract.State()

    override fun handleEvents(event: SettingAccountContract.Event) {
        Log.d(TAG, "handleEvents() - event: $event")
        when (event) {
            // 수정 모드 진입
            SettingAccountContract.Event.OnEditClick -> {
                Log.d(TAG, "OnEditClick - Entering edit mode")
                // TODO: 실제로는 서버에서 현재 사용자 정보를 가져와야 함
                // 여기서는 임시로 더미 데이터 사용
                setState {
                    copy(
                        isEditMode = true,
                        name = "김가영",
                        birth = "2003.12.20",
                        phoneNumber = "010-8991-3803",
                        email = "gy12203803@gmail.com",
                        // 원본 값 저장
                        originalName = "김가영",
                        originalBirth = "2003.12.20",
                        originalPhoneNumber = "010-8991-3803",
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

    companion object {
        private const val TAG = "SettingAccountViewModel"
    }
}
