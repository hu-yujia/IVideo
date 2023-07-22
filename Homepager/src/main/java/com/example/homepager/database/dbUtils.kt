package com.example.homepager.database

import android.content.Context

val Context.homeDatabase
    get() = applicationContext.javaClass.getField("homeDatabase").get(applicationContext) as HomeDatabase
