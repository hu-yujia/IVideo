package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.homepager.dao.VideoDao
import com.example.homepager.model.Comment
import com.example.homepager.net.HomeService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class CommentViewModel(val videoDao: VideoDao) :BaseViewModel<CommentIntent,CommentState>() {
    private val  service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is CommentIntent.LoadLocal->loadVideo(it.id)
                    is CommentIntent.LoadComment->loadComment(it.itemId)
                    is CommentIntent.PublishComment->publishComment(it.comment)
                }
            }
        }
    }
    fun loadVideo(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            state.value = CommentState.LocalResponse(videoDao.getVideoById(id))
        }
    }
    fun publishComment(comment:Comment){
        viewModelScope.launch {
            state.value = try {
                val res=service.publishComment(comment)
                if(res.code==0){
                    CommentState.PublishResponse(res.data)
                }else{
                    CommentState.Error(res.msg)
                }
            }catch (e:Exception){
                CommentState.Error(e.message?:"")
            }
        }
    }
    fun loadComment(itemId:String){
        viewModelScope.launch {
            state.value = try {
                val res=service.getgetCommentByUserId(0,itemId)
                if(res.code==0){
                    CommentState.CommentResponse(res.data)
                }else{
                    CommentState.Error(res.msg)
                }
            }catch (e:Exception){
                CommentState.Error(e.message?:"")
            }
        }
    }
}