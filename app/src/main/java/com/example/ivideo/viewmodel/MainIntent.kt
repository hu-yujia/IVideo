package com.example.ivideo.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class MainIntent:IIntent {
    data class Start(val count:Int):MainIntent()
}