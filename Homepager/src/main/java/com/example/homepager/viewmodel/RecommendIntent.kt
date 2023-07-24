package com.example.homepager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvicore.viewmodel.IIntent

sealed class RecommendIntent:IIntent {

    data class GetRecommendLocalVideo(val list:List<ViewModel>):RecommendIntent()
    data class GetRecommendRemoteVideo(val page:Int,val pagesize:Int):RecommendIntent()
}