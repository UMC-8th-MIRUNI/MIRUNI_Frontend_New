package com.miruni.core.data.fcm

import android.content.Context
import android.provider.Settings
import com.miruni.core.domain.fcm.DeviceIdProvider
import javax.inject.Inject

class DeviceIdProviderImpl @Inject constructor(
    private val context: Context
) : DeviceIdProvider {
    override fun getDeviceId(): String {
        // ANDROID_ID를 추출하여 반환
        return Settings.Secure.getString(
            context.contentResolver, 
            Settings.Secure.ANDROID_ID
        )
    }
}