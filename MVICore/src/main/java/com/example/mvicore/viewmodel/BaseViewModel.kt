package com.example.mvicore.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class BaseViewModel<INTENT:IIntent,STATE:IState>:ViewModel() {
    val intent:Channel<INTENT> by lazy {Channel(Channel.UNLIMITED)}
    val state:Flow<STATE?> by lazy { MutableStateFlow(null) }
}