package com.example.ivideo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ivideo.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class MainState:IState {
    data class Progress(val v:Int):MainState()
    data class Finish(val s:String):MainState()
}