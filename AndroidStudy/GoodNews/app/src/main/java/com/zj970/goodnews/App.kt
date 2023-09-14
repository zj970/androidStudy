package com.zj970.goodnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.zj970.goodnews.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var db: AppDatabase
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        db = AppDatabase.getInstance(context)
        instance = this
    }
}
