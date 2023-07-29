package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.homepager.dao.VideoDao
import com.example.homepager.model.BulletScreen
import com.example.homepager.net.HomeService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetialsViewModel(val videoDao: VideoDao) :BaseViewModel<DetailsIntent,DetialsState>() {
    private val service= retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is DetailsIntent.LoadLocal->loadVideo(it.id)
                    is DetailsIntent.LoadBulletScreen->loadBulletSrceen(it.itemId)
                    is DetailsIntent.SendBulletScreen->sendBulletSrceen(it.bulletScreen)
                }
            }
        }
    }
    fun loadVideo(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            state.value = DetialsState.LocalResponse(videoDao.getVideoById(id))
        }
    }
    fun sendBulletSrceen(bulletScreen: BulletScreen){
        viewModelScope.launch {
            state.value = try {
                val res = service.sendBulletScreen(bulletScreen)
                if(res.code==0){
                    DetialsState.SendBulletScreenResponse(res.data)
                }else{
                    DetialsState.Error(res.msg)
                }
            }catch (e:Exception){
                DetialsState.Error(e.message?:"")
            }
        }

    }
    fun loadBulletSrceen(itemId:String){
        viewModelScope.launch {
            state.value = try {
                val res = service.getBulletScreenInfo(0,itemId)
                if(res.code==0){
                    DetialsState.BulletScreenResponse(res.data)
                }else{
                    DetialsState.Error(res.msg)
                }
            }catch (e:Exception){
                DetialsState.Error(e.message?:"")
            }
        }

    }
}