package com.example.live.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.live.model.LiveRoom
import com.example.live.xmpp.XMPPManager
import com.example.mvicore.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class LiveRoomViewModel:BaseViewModel<LiveRoomIntent,LiveRoomState>() {
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                     LiveRoomIntent.LoadRoom->loadRoom()
                }
            }
        }
    }
    fun loadRoom(){
        viewModelScope.launch (Dispatchers.IO){

//                LiveRoom("小荠","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("一花","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("二乃","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("三玖","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("四叶","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("五月","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("六花","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("七宫","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("八重","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("九条","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
//                LiveRoom("十花","https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一"),
            state.value =try {
                XMPPManager.getChatRooms()?.map {
                    LiveRoom(it,"https://tse4-mm.cn.bing.net/th/id/OIP-C.f4qJ6LSnC5elvRRRjNO5rAHaGJ?pid=ImgDet&rs=1","天下第一") }
                    ?.let { LiveRoomState.RoomResponse(it) }
            }catch (e:Exception){
                LiveRoomState.Error(e.message?:"")
            }
        }
    }
}