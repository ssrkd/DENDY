package com.dendy.os

import android.app.Application

class DendyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Automotive initialization logic (e.g. background power hooks, watchdog)
    }
}

