package com.example.homepager

import com.example.homepager.model.VideoModel
import com.example.homepager.net.HomeService
import com.example.network.model.User
import com.example.network.retrofit
import com.example.network.user
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
//    @Test
//    fun testBulletScreen(){ user
//        user=User(0,"","",0,false,"","","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJuaWtvIn0.75yTASnYIQGrKwybCgWBhZnndJramMYhBmt9YczWlGc","")
//        val service = retrofit.create(HomeService::class.java)
//        runBlocking {
//            val videoModels = service.getgetCommentByUserId(1, "6936590061114753549").data
//                videoModels.forEach(::println)
//
//            }
//        }

}