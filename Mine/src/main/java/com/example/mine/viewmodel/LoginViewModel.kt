package com.example.mine.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mvicore.viewmodel.BaseViewModel
import com.example.network.UserService
import com.example.network.model.User
import com.example.network.retrofit
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class LoginViewModel :BaseViewModel<LoginIntent,LoginState>() {
    val service= retrofit.create(UserService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect{
                when(it){
                    is LoginIntent.Login ->login(it.username,it.password)
                }
            }
        }
    }

    suspend fun login(username:String, password:String){
        state.value=try {
            val res= service.login(username,password)
            if (res.code==0) {
                LoginState.Response(res.data)
            }else{
                LoginState.Error(res.msg)
            }
        }catch (e:Exception){
            LoginState.Error(e.message?:"")
        }

    }
}