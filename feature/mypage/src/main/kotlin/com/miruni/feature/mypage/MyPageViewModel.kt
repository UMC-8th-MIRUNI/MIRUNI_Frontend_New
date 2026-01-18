package com.miruni.feature.mypage

import android.util.Log
import com.miruni.core.common.BaseViewModel
import com.miruni.feature.mypage.domain.ProfileImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MyPageViewModel @Inject constructor() :
    BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {

    init {
        Log.d("MyPageVM", "VM created: $this")
    }

    private val _state = MutableStateFlow(MyPageContract.State())
    val state: StateFlow<MyPageContract.State> = _state.asStateFlow()

    override fun setInitialState(): MyPageContract.State = MyPageContract.State()

    override fun handleEvents(event: MyPageContract.Event) {
        Log.d("MyPageViewModel", "Event: $event")
        when (event) {
            MyPageContract.Event.OnTopBarEditClick -> {
                _state.update { currentState ->
                    currentState.copy(
                        isEditMode = true,
                        isCompleteEnabled = false
                    )
                }
            }
            MyPageContract.Event.OnTopBarNotificationClick -> {}
            MyPageContract.Event.OnEditProfileClick -> {}
            MyPageContract.Event.OnCompleteClick -> {
                _state.update {
                    it.copy(
                        isEditMode = false,
                        isCompleteEnabled = true
                    )
                }
                // TODO 서버 저장 or 로컬 저장
            }

            is MyPageContract.Event.OnNicknameChange -> {
                _state.update {
                    it.copy(
                        nickName = event.nickname,
                        isCompleteEnabled = false
                    )
                }
            }

            MyPageContract.Event.OnProfileImagePrevClick -> {
                _state.update { state ->
                    val size = state.profileImages.size
                    val newIndex =
                    (state.selectedProfileImageIndex - 1 + size) % size

                    state.copy(
                        selectedProfileImageIndex = newIndex,
                        isCompleteEnabled = false
                    )
                }
            }

            MyPageContract.Event.OnProfileImageNextClick -> {
                _state.update { state ->
                    val size = state.profileImages.size
                    val newIndex =
                        (state.selectedProfileImageIndex + 1) % size

                    state.copy(
                        selectedProfileImageIndex = newIndex,
                        isCompleteEnabled = false
                    )
                }
            }

            MyPageContract.Event.OnSettingAccountClick -> setEffect {
                MyPageContract.Effect.Navigation.NavigateToSettingAccount
            }

            MyPageContract.Event.OnSettingNotificationClick -> setEffect {
                MyPageContract.Effect.Navigation.NavigateToSettingNotification
            }

            MyPageContract.Event.OnInfoClick -> setEffect {
                MyPageContract.Effect.Navigation.NavigateToInfo
            }
        }
    }

}
fun dummyProfileImage(): List<ProfileImage> =
    listOf(
        ProfileImage(R.drawable.betty),
        ProfileImage(R.drawable.janet),
        ProfileImage(R.drawable.jonas),
        ProfileImage(R.drawable.mark),
        ProfileImage(R.drawable.tracy),
    )