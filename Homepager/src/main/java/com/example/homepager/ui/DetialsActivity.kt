package com.example.homepager.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.lifecycle.GSYLifecycleObServer
import com.example.common.logDebug
import com.example.homepager.R
import com.example.homepager.adapter.DetialsAdapter
import com.example.homepager.database.homeDatabase
import com.example.homepager.databinding.ActivityDetialBinding
import com.example.homepager.model.VideoModel
import com.example.homepager.viewmodel.DetailsIntent
import com.example.homepager.viewmodel.DetialsState
import com.example.homepager.viewmodel.DetialsViewModel
import com.example.mvicore.BaseActivity
import kotlinx.coroutines.launch

@Route(path = "/homemodel/detials")
class DetialsActivity : BaseActivity<ActivityDetialBinding,DetialsViewModel>() {
    @Autowired(name = "id")
    @JvmField
    var id:Int=0
    lateinit var video:VideoModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        initView()
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadLocal(id))
        }
    }
    fun initView(){
        binding.page.adapter=DetialsAdapter(supportFragmentManager,id)
        binding.tab.setupWithViewPager(binding.page)
        lifecycle.addObserver(GSYLifecycleObServer(binding.video))
        binding.video.backButton.setOnClickListener { finish() }
    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetialsViewModel(homeDatabase.getVideoDao()) } }


    fun loaded(response: DetialsState.LocalResponse){
        logDebug(response.data)
        video=response.data
        binding.video.setUp(video.videopath,true,video.title)
    }

}