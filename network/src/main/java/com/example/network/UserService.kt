package com.example.network

import com.example.network.model.ApiRes
import com.example.network.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


interface UserService {
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username:String, @Field("password") password:String):ApiRes<User>
    @POST("/user/register")
    @FormUrlEncoded
    suspend fun register(@Field("username") username:String, @Field("password") password:String): ApiRes<User>
}