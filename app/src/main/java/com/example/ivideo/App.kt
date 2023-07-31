package com.example.ivideo

import android.app.Application
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseApplication
import com.example.homepager.database.HomeDatabase
import com.example.network.gson
import com.example.network.model.User
import com.example.network.user

class App: BaseApplication() {
    lateinit var homeDatabase: HomeDatabase
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)

        homeDatabase=Room.databaseBuilder(this,HomeDatabase::class.java,"homeDatabase").build()
        val preferences = getSharedPreferences("login", MODE_PRIVATE)
//        preferences.edit().remove("user").apply()
        preferences.getString("user",null)?.let{user
            user = gson.fromJson(it,User::class.java)
        }
    }
}