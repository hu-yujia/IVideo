package com.example.homepager.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.homepager.dao.SimpleTypeDao
import com.example.homepager.net.HomeService
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomepageViewModel(val dao:SimpleTypeDao):BaseViewModel<HomepageIntent, HomepageState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    HomepageIntent.LoadType -> loadType()
                }
            }
        }
    }
    fun loadType() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value=HomepageState.LocalResponse(dao.getAll())
            state.value = try {
                val res = service.getSimpleType()
                if (res.code == 0) {
//                    dao.insert(*res.data.toTypedArray())
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