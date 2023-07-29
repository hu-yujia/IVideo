package com.example.homepager.viewmodel

import com.example.homepager.model.BulletScreen
import com.example.mvicore.viewmodel.IIntent

sealed class DetailsIntent:IIntent {
    data class LoadLocal(val id:Int):DetailsIntent()
    data class LoadBulletScreen(val itemId:String):DetailsIntent()
    data class SendBulletScreen(val bulletScreen:BulletScreen):DetailsIntent()
}