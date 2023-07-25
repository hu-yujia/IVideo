package com.example.mine.viewmodel

import com.example.mvicore.viewmodel.IState
import com.example.network.model.User

sealed class LoginState:IState {
    data class Error(val msg:String):LoginState()
    data class Response(val user:User):LoginState()
}