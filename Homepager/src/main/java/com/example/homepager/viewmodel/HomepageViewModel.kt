package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.homepager.net.HomeService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomepageViewModel:BaseViewModel<HomepageIntent, HomepageState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is HomepageIntent.LoadPage -> loadPage(it)
                }
            }
        }
    }
    fun loadPage(loadPage: HomepageIntent.LoadPage) {
        viewModelScope.launch {
            state.value = try {
                val res = service.getRecommendSimpleVideo(loadPage.page, 10)
                if (res.code == 0) {
                    HomepageState.Response(res.data)
                } else {
                    HomepageState.Error(res.msg)
                }
            } catch (e: Exception) {
                HomepageState.Error(e.message ?: "")
            }
        }
    }
}