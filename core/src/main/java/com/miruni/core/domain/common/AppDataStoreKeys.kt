package com.miruni.core.domain.common

import androidx.datastore.preferences.core.booleanPreferencesKey

object AppDataStoreKeys {
    val AUTO_LOGIN_ENABLED = booleanPreferencesKey("setting_login_auto")
}