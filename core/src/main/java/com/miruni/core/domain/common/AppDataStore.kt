package com.miruni.core.domain.common

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    suspend fun <T> get(key: Preferences.Key<T>): T?
    suspend fun <T> put(key: Preferences.Key<T>, value: T)
    suspend fun <T> remove(key: Preferences.Key<T>)
    suspend fun clear()

    fun <T> observe(key: Preferences.Key<T>, default: T): Flow<T>
    fun <T> observeNullable(key: Preferences.Key<T>): Flow<T?>
}
