package com.example.homepager.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel

@Dao
interface VideoDao {
    @Upsert
    fun insert(vararg types: VideoModel)

    @Query("select * from VideoModel")
    fun getAll():List<VideoModel>

    @Query("select * from VideoModel where channelid=:channelId limit 0 ,:pageSize")
    fun getByChannelId(channelId:String,pageSize:Int):List<VideoModel>
    @Query("select * from VideoModel where id = :id")
    fun getVideoById(id:Int):VideoModel
}