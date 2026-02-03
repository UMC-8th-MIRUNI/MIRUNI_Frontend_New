package com.miruni.feature.login.domain.repository

interface PermissionProvider {
    fun hasNotificationPermission(): Boolean
}