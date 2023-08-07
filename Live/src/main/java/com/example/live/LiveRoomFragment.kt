package com.example.live

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.common.adapter.CommonAdapter
import com.example.common.logDebug
import com.example.live.databinding.FragmentLiveRoomBinding
import com.example.live.model.LiveRoom
import com.example.live.viewmodel.LiveRoomIntent
import com.example.live.viewmodel.LiveRoomState
import com.example.live.viewmodel.LiveRoomViewModel
import com.example.mvicore.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class LiveRoomFragment : BaseFragment<FragmentLiveRoomBinding,LiveRoomViewModel>() {
    val adapter by lazy { CommonAdapter<LiveRoom>(R.layout.item_room, BR.room) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(LiveRoomIntent.LoadRoom)
        }
        binding.recycler.adapter = adapter
    }
    fun loaded(response:LiveRoomState.RoomResponse) {
        adapter += response.data
    }
    fun error(error:LiveRoomState.Error) {
        Snackbar.make(binding.root, error.msg, Snackbar.LENGTH_LONG).show()
        logDebug(error)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            LiveRoomFragment()
    }
}