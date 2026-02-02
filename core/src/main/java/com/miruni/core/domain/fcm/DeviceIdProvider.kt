package com.miruni.core.domain.fcm

interface DeviceIdProvider {
    fun getDeviceId(): String
}