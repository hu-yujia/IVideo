package com.example.live

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.live.databinding.ActivityPublishBinding

import com.ivideo.avcore.rtmplive.Config
import com.ivideo.avcore.rtmplive.MediaPublisher


@Route(path = "/live/PublishActivity")
class PublishActivity : AppCompatActivity() {
    lateinit var binding:ActivityPublishBinding
    lateinit var mediaPublisher:MediaPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_publish)
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            it.values.forEach { b->
                if(b){
                    finish()
                }
            }
        }.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO))
    }

    override fun onResume() {
        super.onResume()
        mediaPublisher= MediaPublisher.newInstance(
            Config.Builder()
                .setFps(60)
                .setUrl(BuildConfig.RTMP_URL)
                .build()
        )
//        binding.surfaceView.setCallback()
    }
}