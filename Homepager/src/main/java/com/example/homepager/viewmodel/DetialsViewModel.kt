package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.homepager.dao.VideoDao
import com.example.mvicore.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DetialsViewModel(val videoDao: VideoDao) :BaseViewModel<DetailsIntent,DetialsState>() {
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is DetailsIntent.LoadLocal->loadVideo(it.id)
                }
            }
        }
    }
    fun loadVideo(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            state.value = DetialsState.LocalResponse(videoDao.getVideoById(id))
        }
    }
}