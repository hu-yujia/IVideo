package com.example.common

import android.app.Application

open class BaseApplication:Application() {


    companion object{
        lateinit var appContext:BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        appContext=this
    }
}