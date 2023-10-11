package com.example.dummyypapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DummyApplication: Application() {

    companion object {
        private lateinit var instance: DummyApplication
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
       // Hilt.init(this)

    }
}