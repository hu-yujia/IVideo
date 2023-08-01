package com.example.live.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class LiveRoomIntent:IIntent {
     object LoadRoom:LiveRoomIntent()
}