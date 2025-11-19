package com.miruni.miruni_fe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MiruniApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}