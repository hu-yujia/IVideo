package com.example.homepager.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
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
import com.example.homepager.model.BulletScreen
import com.example.homepager.model.Comment
import com.example.homepager.model.VideoModel
import com.example.homepager.viewmodel.CommentIntent
import com.example.homepager.viewmodel.DetailsIntent
import com.example.homepager.viewmodel.DetialsState
import com.example.homepager.viewmodel.DetialsViewModel
import com.example.mvicore.BaseActivity
import com.example.network.user
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import kotlinx.coroutines.launch
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.IDanmakus
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.R2LDanmaku
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser

@Route(path = "/homemodel/detials")
class DetialsActivity : BaseActivity<ActivityDetialBinding,DetialsViewModel>() {
    @Autowired(name = "id")
    @JvmField
    var id:Int=0
    lateinit var video:VideoModel
    lateinit var danmakuContext:DanmakuContext
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
        binding.video.setVideoAllCallBack(object :GSYSampleCallBack(){
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
            }
        })

        binding.video.backButton.setOnClickListener { finish() }
        binding.video.setVideoAllCallBack(object :GSYSampleCallBack(){
            override fun onStartPrepared(url: String?, vararg objects: Any?) {
                binding.danmaku.start()
            }
        })
        binding.danmaku.enableDanmakuDrawingCache(true)
        danmakuContext=DanmakuContext.create()
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN,3F)
        danmakuContext.setDuplicateMergingEnabled(true)
        danmakuContext.setScrollSpeedFactor(1.2f)
        binding.danmaku.prepare(object :BaseDanmakuParser(){
            override fun parse(): IDanmakus =Danmakus()
        },danmakuContext)
        binding.send.setOnClickListener{
            if (user ==null) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            }else if(binding.bullet.text.isNullOrEmpty()){
                Toast.makeText(this, "评论为空", Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch {
                    viewModel.intent.send(DetailsIntent.SendBulletScreen(BulletScreen(datatype = 0, content = binding.bullet.text.toString(), itemid = video.item_id)))
                }
            }
        }
    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetialsViewModel(homeDatabase.getVideoDao()) } }


    fun loaded(response: DetialsState.LocalResponse){
        logDebug(response.data)
        video=response.data
        binding.video.setUp(video.videopath,true,video.title)
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadBulletScreen(video.item_id))
        }
    }
    fun loadBulletScreen(response: DetialsState.BulletScreenResponse){
        response.data.map {
            logDebug(it)
            danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL).apply {
                this.text=it.content
                this.textColor=Color.WHITE
                this.borderColor=Color.BLACK
                this.textSize=100f
            }
        }.forEach(binding.danmaku::addDanmaku)

       // Toast.makeText(this, "弹幕装载完毕", Toast.LENGTH_SHORT).show()
    }
    fun sendSuccessed(response: DetialsState.SendBulletScreenResponse){
        danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL).apply {
            this.text=response.data.content
            this.textColor=Color.WHITE
            this.borderColor=Color.BLACK
            this.textSize=100f
            binding.danmaku.addDanmaku(this)
        }
        Toast.makeText(this, "弹幕发送成功", Toast.LENGTH_SHORT).show()
        binding.bullet.text=null

    }
    fun error(error: DetialsState.Error){
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
    }

}