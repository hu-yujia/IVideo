package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.common.logDebug
import com.example.homepager.dao.VideoDao
import com.example.homepager.net.HomeService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SimpleTypeViewModel(val dao:VideoDao):BaseViewModel<SimpleTypeIntent,SimpleTypeState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                logDebug(it)
                when(it){
                    is SimpleTypeIntent.GetLoacalVideo-> getVideo(it.channelId)
                    is SimpleTypeIntent.GetRemoteVideo->getRemoteVideo(it.channelId,it.page)
                    else -> {}
                }
            }
        }
    }
    fun getVideo(channelId:String){
        viewModelScope.launch(Dispatchers.IO) {
            state.value=SimpleTypeState.LocalResponse(dao.getByChannelId(channelId,10))
        }
    }
    fun getRemoteVideo(channelId:String,page:Int){
        logDebug("channelId: $channelId" )
        viewModelScope.launch(Dispatchers.IO) {
            state.value=try {
                val res=service.getSimpleVideoByChannelId(channelId,page,10)
                logDebug(res)
                if(res.code==0){
                    dao.insert(*res.data.toTypedArray())
                    val response = SimpleTypeState.RemoteResponse(res.data)
                    if(state.value==response) {
                       SimpleTypeState.Error("没有数据了捏~")
                    }else{
                        response
                    }
                }else{
                    SimpleTypeState.Error(res.msg)
                }
            }catch (e:Exception){
                e.printStackTrace()
                SimpleTypeState.Error(e.message?:"网络错误")
            }
        }
    }
}