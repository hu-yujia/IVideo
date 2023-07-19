package com.example.ivideo.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.ivideo.net.MainService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel:BaseViewModel<MainIntent,MainState>() {
    val service= retrofit.create(MainService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    MainIntent.GetSimpleVideo->{
                        state.value=
                        try {
                            val res = service.getRecommendSimpleVideo()
                            if(res.code==0){
                                MainState.Response(res.data)
                            }else{
                                MainState.Error(res.msg)
                            }
                        }catch (e:Exception){
                            MainState.Error(e.message?:"")
                        }

                    }
                }
            }
        }
    }
}