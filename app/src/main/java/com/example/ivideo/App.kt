package com.example.ivideo

import android.app.Application
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.homepager.database.HomeDatabase

class App:Application() {
    lateinit var homeDatabase: HomeDatabase
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)

        homeDatabase=Room.databaseBuilder(this,HomeDatabase::class.java,"homeDatabase").build()
    }
}