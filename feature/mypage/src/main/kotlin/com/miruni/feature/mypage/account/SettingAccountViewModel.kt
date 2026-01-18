package com.miruni.feature.mypage.account

import android.util.Log
import com.miruni.core.common.BaseViewModel
import jakarta.inject.Inject

class SettingAccountViewModel @Inject constructor() :
    BaseViewModel<SettingAccountContract.Event, SettingAccountContract.State, SettingAccountContract.Effect>() {

//    private val _state = MutableStateFlow(SettingAccountContract.State())
//    val state: StateFlow<SettingAccountContract.State> = _state.asStateFlow()

    override fun setInitialState(): SettingAccountContract.State = SettingAccountContract.State()

    override fun handleEvents(event: SettingAccountContract.Event) {
        when (event) {
            SettingAccountContract.Event.OnEditClick -> {
                Log.d("SettingAccountViewModel", "edit mode true")
                setState {
                    copy(
                        isEditMode = true,
                        name = "김가영",
                        birth = "2003.12.20",
                        phoneNumber = "010-8991-3803",
                        email = "gy12203803@gmail.com"
                    )
                }
//                _state.update { currentState ->
//                    currentState.copy(
//                        isEditMode = true,
//                        name = "김가영",
//                        birth = "2003.12.20",
//                        phoneNumber = "010-8991-3803",
//                        email = "gy12203803@gmail.com"
//                    )
//                }
            }

            SettingAccountContract.Event.OnCompleteClick -> {
                setState {
                    copy(
                        isSaving = true,
                    )
                }
//                viewModelScope.launch {
//                    val result = SettingAccountContract.State(
//                        name = state.value.name,
//                        birth = state.value.birth,
//                        phoneNumber = state.value.phoneNumber,
//                    )
//
//                    when (result) {
                        // TODO: 서버 저장
//                        is DataResult.Success -> {
//                            // 저장 성공 -> 수정 모드 해제
//                            setState {
//                                copy(
//                                    isSaving = false
//                                )
//                            }
//                        }

//                        is DataResult.Error -> {
//                            // 저장 실패 -> 다시 수정모드 복구
//                            setState {
//                                copy(
//                                    isSaving = false,
//                                    isEditMode = true
//                                )
//                            }
//                        }
//                    }
//                }
            }

            is SettingAccountContract.Event.OnBirthChange ->
                setState { copy(birth = event.value) }

            is SettingAccountContract.Event.OnNameChange ->
                setState { copy(name = event.value) }

            is SettingAccountContract.Event.OnPhoneChange ->
                setState { copy(phoneNumber = event.value) }
        }
    }
}
