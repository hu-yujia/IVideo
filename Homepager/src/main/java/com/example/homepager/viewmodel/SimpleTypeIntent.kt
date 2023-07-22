package com.example.homepager.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class SimpleTypeIntent:IIntent {
    data class GetLoacalVideo(val channelId:String):SimpleTypeIntent()
    data class GetRemoteVideo(val channelId:String,val page:Int):SimpleTypeIntent()
}