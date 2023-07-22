package com.example.homepager.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homepager.dao.SimpleTypeDao
import com.example.homepager.dao.VideoDao
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel

@Database(entities = [SimpleType::class,VideoModel::class], version = 2)
abstract class HomeDatabase:RoomDatabase() {
    abstract fun getSimpleTypeDao():SimpleTypeDao
    abstract fun getVideoDao():VideoDao

}
