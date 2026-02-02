package com.miruni.feature.login.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.miruni.feature.login.domain.repository.PermissionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionProviderImpl(
    private val context: Context
) : PermissionProvider {
    override fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, 
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}