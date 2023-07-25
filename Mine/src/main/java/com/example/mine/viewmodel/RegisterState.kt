package com.example.mine.viewmodel

import com.example.mvicore.viewmodel.IState
import com.example.network.model.User

sealed class RegisterState:IState {
    data class Error(val msg:String):RegisterState()
    data class Response(val user:User):RegisterState()
}