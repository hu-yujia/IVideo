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

open class RecommendViewModel(val dao:VideoDao):BaseViewModel<RecommendIntent,RecommendState>() {
    val service= retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is RecommendIntent.GetRecommendLocalVideo->getVideo()
                    is RecommendIntent.GetRecommendRemoteVideo->getRemoteVideo(1,10)

                }
            }
        }
    }

    fun getVideo(){
        viewModelScope.launch(Dispatchers.IO) {
            state.value=RecommendState.LocalRecommendResponse(dao.getAll())
        }
    }
    fun getRemoteVideo(page:Int,pagesize:Int){
        viewModelScope.launch(Dispatchers.IO) {
            state.value=try {
                val res=service.getRecommendSimpleVideo(page,pagesize)
                if(res.code==0){
                    dao.insert(*res.data.toTypedArray())
                    val response = RecommendState.RemoteRecommendResponse(res.data)
                    if(state.value==response) {
                        RecommendState.Error("没有数据了捏~")
                    }else{
                        response
                    }
                }else{
                    RecommendState.Error(res.msg)
                }
            }catch (e: Exception){
                e.printStackTrace()
                RecommendState.Error(e.message?:"网络错误")
            }
        }
    }
}