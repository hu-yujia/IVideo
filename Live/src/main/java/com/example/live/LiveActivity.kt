package com.example.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.lifecycle.GSYLifecycleObServer
import com.example.live.databinding.ActivityLiveBinding
@Route(path = "/live/LiveActivity")
class LiveActivity : AppCompatActivity() {
    val binding:ActivityLiveBinding by lazy { DataBindingUtil.setContentView(this,R.layout.activity_live) }
    @Autowired
    lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        ARouter.getInstance().inject(this)
        binding.player.setUp("",false,title)
        binding.player.backButton.setOnClickListener {
            finish()
        }
        lifecycle.addObserver(GSYLifecycleObServer(binding.player))
    }
}