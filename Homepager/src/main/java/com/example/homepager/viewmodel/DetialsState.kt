package com.example.homepager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.homepager.model.BulletScreen
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class DetialsState:IState {
    data class LocalResponse(val data:VideoModel):DetialsState()
    data class BulletScreenResponse(val data:List<BulletScreen>):DetialsState()
    data class SendBulletScreenResponse(val data:BulletScreen):DetialsState()

    data class Error(val msg:String):DetialsState()
}