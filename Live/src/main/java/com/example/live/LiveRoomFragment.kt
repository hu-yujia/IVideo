package com.example.live

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.common.adapter.CommonAdapter
import com.example.live.databinding.FragmentLiveRoomBinding
import com.example.live.model.LiveRoom
import com.example.live.viewmodel.LiveRoomIntent
import com.example.live.viewmodel.LiveRoomState
import com.example.live.viewmodel.LiveRoomViewModel
import com.example.mvicore.BaseFragment
import kotlinx.coroutines.launch


class LiveRoomFragment : BaseFragment<FragmentLiveRoomBinding,LiveRoomViewModel>() {
    val adapter by lazy {CommonAdapter<LiveRoom>(R.layout.item_room,BR.liveModel)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(LiveRoomIntent.LoadRoom)
        }
        binding.rv.adapter=adapter
    }

    fun loaded(response: LiveRoomState.RoomResponse){
        adapter+=response.data
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            LiveRoomFragment()
    }
}