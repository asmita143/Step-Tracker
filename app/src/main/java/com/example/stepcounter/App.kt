package com.example.stepcounter

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
//import com.example.stepcounter.work.RefreshDataWorker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App: Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("CONTEXT", "App onCreate()")
        appContext = applicationContext
//        delayedInit()
    }

//    @OptIn(DelicateCoroutinesApi::class)
//    private fun delayedInit() {
//        GlobalScope.launch {
//            setupRecurringWork()
//        }
//    }
//
//    private fun setupRecurringWork() {
//        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(14, TimeUnit.DAYS)
//            .build()
//        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
//            RefreshDataWorker.WORK_NAME,
//            ExistingPeriodicWorkPolicy.KEEP,
//            repeatingRequest)
//    }
}