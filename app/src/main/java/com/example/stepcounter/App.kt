package com.example.stepcounter

import android.app.Application
import android.content.Context
import android.util.Log

class App: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("CONTEXT", "App onCreate()")
        appContext = applicationContext
    }
}