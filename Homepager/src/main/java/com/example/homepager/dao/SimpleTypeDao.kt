package com.example.homepager.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.homepager.model.SimpleType

@Dao
interface SimpleTypeDao {
    @Upsert
    fun insert(vararg types: SimpleType)

    @Query("select * from SimpleType")
    fun getAll():List<SimpleType>
}