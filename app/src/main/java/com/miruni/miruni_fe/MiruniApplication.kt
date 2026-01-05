package com.miruni.miruni_fe

import android.app.Application
import com.miruni.network.TokenProvider
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MiruniApplication : Application() {

    @Inject lateinit var tokenProvider: TokenProvider

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            tokenProvider.init()
        }
    }
}