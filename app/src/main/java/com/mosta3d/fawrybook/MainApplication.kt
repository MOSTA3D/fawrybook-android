package com.mosta3d.fawrybook

import android.app.Application
import com.mosta3d.fawrybook.auth.session.AuthSessionManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application() {
    @Inject lateinit var authSessionManager: AuthSessionManager

    override fun onCreate() {
        super.onCreate()
        // Force creation so it starts collecting NetworkEventBus immediately.
        authSessionManager
    }
}