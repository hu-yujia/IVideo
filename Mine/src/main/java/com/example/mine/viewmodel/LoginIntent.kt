package com.example.mine.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class LoginIntent:IIntent {
    data class Login(val username:String,val password:String):LoginIntent()
}