package com.example.ivideo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ivideo.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class MainState:IState {
    data class Error(val msg:String):MainState()
    data class Response(val data:List<VideoModel>):MainState()
}