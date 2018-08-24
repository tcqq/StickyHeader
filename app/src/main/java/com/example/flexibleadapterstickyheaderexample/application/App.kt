package com.example.flexibleadapterstickyheaderexample.application

import android.app.Application
import com.example.flexibleadapterstickyheaderexample.BuildConfig
import timber.log.Timber

/**
 * @author Alan Dreamer
 * @since 24/08/2018 Created
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}