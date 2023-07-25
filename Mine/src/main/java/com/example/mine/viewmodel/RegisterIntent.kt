package com.example.mine.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class RegisterIntent:IIntent {
    data class register(val username:String,val password:String):RegisterIntent()
}