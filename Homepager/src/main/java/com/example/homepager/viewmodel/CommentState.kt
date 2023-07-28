package com.example.homepager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.homepager.model.Comment
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.mvicore.viewmodel.IState

sealed class CommentState:IState {
    data class LocalResponse(val data:VideoModel):CommentState()
    data class CommentResponse(val data:List<Comment>):CommentState()
    data class PublishResponse(val data:Comment):CommentState()
    data class Error(val msg:String):CommentState()
}