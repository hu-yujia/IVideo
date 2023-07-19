package com.example.ivideo.net

import com.example.ivideo.model.VideoModel
import com.example.network.model.ApiRes
import retrofit2.http.GET

interface MainService {
    @GET("/videosimple/getRecommendSimpleVideo?page=1&pagesize=1")
    suspend fun getRecommendSimpleVideo():ApiRes<List<VideoModel>>
}