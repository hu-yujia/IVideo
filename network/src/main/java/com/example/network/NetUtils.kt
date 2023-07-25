package com.example.network

import com.example.network.model.ApiRes
import com.example.network.model.User
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


var user: User? = null
private val client by lazy {
    OkHttpClient.Builder()
        .addInterceptor {
            if (user != null) {
                it.proceed(it.request().newBuilder().header("token", user?.token).build())
            } else {
                it.proceed(it.request())
            }
        }
        .build()
}
val gson =GsonBuilder().create()
val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST_URL)
    .client(client)
    .addConverterFactory(object :Converter.Factory(){
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *> {
            return Converter<ResponseBody, Any> { value ->
                val string= value.string()
                return@Converter try {
                    gson.fromJson(string,type)
                }catch (e:Exception){
                    gson.fromJson<ApiRes<String>>(string, TypeToken.getParameterized(ApiRes::class.java,String::class.java).type)
                }
            }
        }

        override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<out Annotation>,
            methodAnnotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<*, RequestBody> {
            return Converter<Any,RequestBody>{value -> RequestBody.create(MediaType.get("application/json"),gson.toJson(value)) }
        }
    })
    .build()