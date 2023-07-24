package com.example.homepager.viewmodel

import com.example.homepager.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class RecommendState:IState {
    data class LocalRecommendResponse(val data:List<VideoModel>):RecommendState()
    data class RemoteRecommendResponse(val data:List<VideoModel>):RecommendState()
    data class Error(val msg:String):RecommendState()
}