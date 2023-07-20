package com.example.homepager.net

import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.network.model.ApiRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/videotype/getSimpleType")
    suspend fun getSimpleType(): ApiRes<List<SimpleType>>
}