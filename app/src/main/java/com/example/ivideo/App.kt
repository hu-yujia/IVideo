package com.example.ivideo

import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseApplication
import com.example.common.logDebug
import com.example.homepager.database.HomeDatabase
import com.example.live.xmpp.XMPPManager
import com.example.network.gson
import com.example.network.model.User
import com.example.network.user
import kotlin.concurrent.thread

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
        thread {
            XMPPManager.init()
            user?.let {
                XMPPManager.register(it.username, it.password)
                val login = XMPPManager.login("tsuki", "111111")
                logDebug("Login:${login}")
                /*
                val multiUserChat = XMPPManager.createChartRoom("Tsuki的房间", "tsuki")
                logDebug("a meeting"+XMPPManager.getChatRooms())
                logDebug(XMPPManager.getChatRooms())
                XMPPManager.destroyChatRoom(multiUserChat)
                logDebug("no a meeting"+XMPPManager.getChatRooms())
                */
            }

        }

    }
}