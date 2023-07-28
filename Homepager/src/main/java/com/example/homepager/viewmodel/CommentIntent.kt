package com.example.homepager.viewmodel

import com.example.homepager.model.Comment
import com.example.mvicore.viewmodel.IIntent

sealed class CommentIntent:IIntent {
    data class LoadLocal(val id:Int):CommentIntent()
    data class LoadComment(val itemId:String):CommentIntent()
    data class PublishComment(val comment:Comment):CommentIntent()
}