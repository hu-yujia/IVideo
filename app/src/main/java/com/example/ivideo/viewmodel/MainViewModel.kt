package com.example.ivideo.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.ivideo.net.MainService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.lang.Exception

class MainViewModel:BaseViewModel<MainIntent, MainState>() {
    val service = retrofit.create(MainService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.Start -> start(it.count)
                }
            }
        }
    }
    fun start(count:Int) {
        viewModelScope.launch(Dispatchers.IO){
            for (i in count downTo 0) {
                state.value = MainState.Progress(i)
                Thread.sleep(1000)
            }
            state.value = MainState.Finish("结束")
        }

    }
}
