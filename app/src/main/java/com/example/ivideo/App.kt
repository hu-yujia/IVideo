package com.example.ivideo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
    }
}