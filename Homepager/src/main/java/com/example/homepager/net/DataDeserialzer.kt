package com.example.homepager.net

import android.annotation.SuppressLint
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.TimeZone


/*
 在deserialize方法中，
 首先通过JsonDeserializationContext的deserialize方法将json元素转换为字符串类型。
 然后，设置日期时间格式为"yyyy-MM-dd HH:mm:ss"，并将时区设置为GMT。
 接着，将字符串解析为日期对象，最后将时区设置为默认时区，并使用SimpleDateFormat的format方法将日期对象格式化为指定格式的字符串。
 */


class DataDeserialzer :JsonDeserializer<String>,JsonSerializer<String>{
    @SuppressLint("SimpleDateFormat")
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


/*
在serialize方法中，
通过JsonSerializationContext的serialize方法将字符串转换为JsonElement对象，并返回该对象。
*/
override fun serialize(
        src: String?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        val s : JsonElement? =context?.serialize(src)
        return s
    }
}