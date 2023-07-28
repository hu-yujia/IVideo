package com.example.homepager.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class DetailsIntent:IIntent {
    data class LoadLocal(val id:Int):DetailsIntent()
}