package com.example.homepager.model

import com.example.homepager.net.DataDeserialzer
import com.google.gson.annotations.JsonAdapter

data class Comment(
    val agreenum: Int=0,
    val content: String,
    @JsonAdapter(DataDeserialzer::class)
    val createtime: String="",
    val datatype: Int,
    val id:Int =0,
    val itemid: String,
    val nickname: String="",
    val replyList: List<Comment> = listOf(),
    val replytotal: Int=0,
    val userid: Int=0,
    val userlogo: String=""
)