package com.miruni.feature.mypage.notification

import android.util.Log
import com.miruni.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingNotificationViewModel @Inject constructor(
) : BaseViewModel<SettingNotificationContract.Event, SettingNotificationContract.State, SettingNotificationContract.Effect>() {

    init {
        Log.d(TAG, "ViewModel created: $this")
    }

    override fun setInitialState(): SettingNotificationContract.State = SettingNotificationContract.State()

    override fun handleEvents(event: SettingNotificationContract.Event) {
        Log.d(TAG, "handleEvents() - event: $event")
        when (event) {
            is SettingNotificationContract.Event.OnReminder5MinChange -> {
                Log.d(TAG, "OnReminder5MinChange - enabled: ${event.enabled}")
                setState {
                    copy(isReminder5MinEnabled = event.enabled)
                }
            }

            is SettingNotificationContract.Event.OnReminder10MinChange -> {
                Log.d(TAG, "OnReminder10MinChange - enabled: ${event.enabled}")
                setState {
                    copy(isReminder10MinEnabled = event.enabled)
                }
            }

            is SettingNotificationContract.Event.OnExecutionPopupChange -> {
                Log.d(TAG, "OnExecutionPopupChange - enabled: ${event.enabled}")
                setState {
                    copy(isExecutionPopupEnabled = event.enabled)
                }
            }

            is SettingNotificationContract.Event.OnExecutionNagChange -> {
                Log.d(TAG, "OnExecutionNagChange - enabled: ${event.enabled}")
                setState {
                    copy(isExecutionNagEnabled = event.enabled)
                }
            }

            is SettingNotificationContract.Event.OnMarketingConsentChange -> {
                Log.d(TAG, "OnMarketingConsentChange - enabled: ${event.enabled}")
                setState {
                    copy(isMarketingConsentEnabled = event.enabled)
                }
            }
        }
    }

    companion object {
        private const val TAG = "SettingNotificationVM"
    }
}
