package com.example.mine.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.UserService
import com.example.network.model.User
import com.example.network.retrofit
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel :BaseViewModel<RegisterIntent,RegisterState>() {
    val service= retrofit.create(UserService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is RegisterIntent.register ->register(it.username,it.password)
                }
            }
        }
    }

    suspend fun register(username:String, password:String){
        state.value=try {
            val res= service.register(username,password)
            if (res.code==0) {
                RegisterState.Response(res.data)
            }else{
                RegisterState.Error(res.msg)
            }
        }catch (e:Exception){
            RegisterState.Error(e.message?:"")
        }

    }
}