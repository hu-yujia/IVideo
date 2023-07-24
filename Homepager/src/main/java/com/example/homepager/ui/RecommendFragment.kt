package com.example.homepager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.common.BR
import com.example.common.adapter.CommonAdapter
import com.example.common.logDebug
import com.example.homepager.R
import com.example.homepager.database.homeDatabase
import com.example.homepager.databinding.FragmentRecommendBinding
import com.example.homepager.model.VideoModel
import com.example.homepager.viewmodel.RecommendIntent
import com.example.homepager.viewmodel.RecommendState
import com.example.homepager.viewmodel.RecommendViewModel
import com.example.homepager.viewmodel.SimpleTypeIntent
import com.example.homepager.viewmodel.SimpleTypeState
import com.example.homepager.viewmodel.SimpleTypeViewModel
import com.example.mvicore.BaseFragment
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [RecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendFragment : BaseFragment<FragmentRecommendBinding,RecommendViewModel>() {
    // TODO: Rename and change types of parameters
    private val adapter by lazy {CommonAdapter<VideoModel>(R.layout.item_video, BR.video)}
    private var page=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(RecommendIntent.GetRecommendRemoteVideo(page,10))
        }
        binding.rv.adapter=adapter
        binding.refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                lifecycleScope.launch {
                    page=1
                    viewModel.intent.send(RecommendIntent.GetRecommendRemoteVideo(page,10))
                }
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                lifecycleScope.launch {
                    viewModel.intent.send(RecommendIntent.GetRecommendRemoteVideo(++page,10))
                }
            }

        })
    }
    fun erro(error: RecommendState.Error){
        binding.refresh.finishRefresh()
        logDebug(error.msg)
        Toast.makeText(context, error.msg, Toast.LENGTH_SHORT).show()
    }

    fun loaded(response: RecommendState.RemoteRecommendResponse){
        logDebug("网络加载${response.data}")
        binding.refresh.finishRefresh()
        if(page==1){
            adapter.clear()
        }
        adapter+=response.data

    }
    fun loadLocal(response: RecommendState.LocalRecommendResponse){
        logDebug("本地加载${response.data}")
        adapter+=response.data
    }
    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = viewModelFactory { initializer { RecommendViewModel(requireContext().homeDatabase.getVideoDao()) } }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecommendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = RecommendFragment()
    }
}