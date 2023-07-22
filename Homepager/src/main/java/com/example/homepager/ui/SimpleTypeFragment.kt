package com.example.homepager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.common.BR
import com.example.common.adapter.CommonAdapter
import com.example.common.logDebug
import com.example.homepager.R
import com.example.homepager.database.homeDatabase
import com.example.homepager.databinding.FragmentSimpleTypeBinding
import com.example.homepager.model.VideoModel
import com.example.homepager.viewmodel.HomepageState
import com.example.homepager.viewmodel.HomepageViewModel
import com.example.homepager.viewmodel.SimpleTypeIntent
import com.example.homepager.viewmodel.SimpleTypeState
import com.example.homepager.viewmodel.SimpleTypeViewModel
import com.example.mvicore.BaseFragment
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CHANNEL_ID = "channelId"


/**
 * A simple [Fragment] subclass.
 * Use the [SimpleTypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimpleTypeFragment : BaseFragment<FragmentSimpleTypeBinding,SimpleTypeViewModel>() {
    // TODO: Rename and change types of parameters
    private lateinit var channelId: String
    private var page=1
    private val adapter by lazy {CommonAdapter<VideoModel>(R.layout.item_video,BR.video)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            channelId = it.getString(CHANNEL_ID,"")

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(SimpleTypeIntent.GetLoacalVideo(channelId))
            viewModel.intent.send(SimpleTypeIntent.GetRemoteVideo(channelId,page))
        }
        binding.rv.adapter=adapter
        binding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
            override fun onRefresh(refreshLayout: RefreshLayout) {
                lifecycleScope.launch {
                    page=1
                    viewModel.intent.send(SimpleTypeIntent.GetRemoteVideo(channelId,page))
                }
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                lifecycleScope.launch {
                    viewModel.intent.send(SimpleTypeIntent.GetRemoteVideo(channelId,++page))
                }
            }

        })
    }
    fun erro(error: SimpleTypeState.Error){
        binding.refresh.finishRefresh()
        logDebug(error.msg)
        Toast.makeText(context, error.msg, Toast.LENGTH_SHORT).show()
    }
    fun loaded(response: SimpleTypeState.RemoteResponse){
        logDebug("网络加载${response.data}")
        binding.refresh.finishRefresh()
        if(page==1){
            adapter.clear()
        }
        adapter+=response.data

    }
    fun loadLocal(response: SimpleTypeState.LocalResponse){
        logDebug("本地加载${response.data}")
        adapter+=response.data
    }
    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = viewModelFactory { initializer { SimpleTypeViewModel(requireContext().homeDatabase.getVideoDao()) } }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment SimpleTypeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SimpleTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(CHANNEL_ID, param1)
                }
            }
    }
}