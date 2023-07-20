package com.example.homepager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homepager.dao.SimpleTypeDao
import com.example.homepager.model.SimpleType

@Database(entities = [SimpleType::class], version = 1)
abstract class HomeDatabase:RoomDatabase() {
    abstract fun getSimpleTypeDao():SimpleTypeDao

}