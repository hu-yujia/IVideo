package com.example.live.model

import com.alibaba.android.arouter.launcher.ARouter

data class LiveRoom (
    val name :String,
    val logo : String,
    val info:String
    )
{
    fun look(){
        ARouter.getInstance().build("/live/LiveActivity").withString("title",name).navigation()
    }
}