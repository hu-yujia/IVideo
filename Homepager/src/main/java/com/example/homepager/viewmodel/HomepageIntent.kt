package com.example.homepager.viewmodel

import com.example.mvicore.viewmodel.IIntent

sealed class HomepageIntent: IIntent {
    object LoadType:HomepageIntent()
}