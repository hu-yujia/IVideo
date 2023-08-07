package com.example.live.viewmodel

import com.example.live.model.LiveRoom
import com.example.mvicore.viewmodel.IState

sealed class LiveRoomState:IState {
    data class RoomResponse(val data:List<LiveRoom>):LiveRoomState()
    data class Error(val msg:String):LiveRoomState()
}