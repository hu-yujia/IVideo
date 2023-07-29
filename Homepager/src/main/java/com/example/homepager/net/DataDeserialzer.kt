package com.example.homepager.net

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.TimeZone

class DataDeserialzer :JsonDeserializer<String>,JsonSerializer<String>{
    private val sdf=SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        val s :String?=context?.deserialize(json,String::class.java)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        val parse = sdf.parse(s)
        sdf.timeZone=TimeZone.getDefault()
        return sdf.format(parse)
    }

    override fun serialize(
        src: String?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        val s : JsonElement? =context?.serialize(src)
        return s
    }
}