package com.example.homepager.viewmodel

import com.example.homepager.model.SimpleType
import com.example.mvicore.viewmodel.IState

sealed class HomepageState:IState {
    data class LocalResponse(val data:List<SimpleType>):HomepageState()
    data class Response(val data:List<SimpleType>):HomepageState()
    data class Error(val msg:String):HomepageState()
}