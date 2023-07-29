package com.example.homepager.net

import com.example.homepager.model.BulletScreen
import com.example.homepager.model.Comment
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.network.model.ApiRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeService {
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/videotype/getSimpleType")
    suspend fun getSimpleType(): ApiRes<List<SimpleType>>

    @GET("/videosimple/getSimpleVideoByChannelId")
    suspend fun getSimpleVideoByChannelId(@Query( "channelId") channelId:String, @Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/bulletscreen/getBulletScreenInfo")
    suspend fun getBulletScreenInfo(@Query("datatype")datatype:Int,@Query("itemid")itemid:String):ApiRes<List<BulletScreen>>

    @GET("/comment/getCommentByUserId")
    suspend fun getgetCommentByUserId(@Query("datatype")datatype:Int,@Query("itemid")itemid:String):ApiRes<List<Comment>>
    @POST("/comment/publishComment")
    suspend fun publishComment(@Body comment: Comment):ApiRes<Comment>
    @POST("/bulletscreen/publishBulletScreen")
    suspend fun sendBulletScreen(@Body bulletScreen: BulletScreen):ApiRes<BulletScreen>
}