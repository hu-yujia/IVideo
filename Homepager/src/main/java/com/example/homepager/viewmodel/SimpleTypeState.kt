package com.example.homepager.viewmodel

import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class SimpleTypeState:IState {
    data class LocalResponse(val data:List<VideoModel>):SimpleTypeState()
    data class RemoteResponse(val data:List<VideoModel>):SimpleTypeState()
    data class Error(val msg:String):SimpleTypeState()
}