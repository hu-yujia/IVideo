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
import com.ivideo.avcore.wiget.CameraGLSurfaceView.OnSurfaceCallback
import javax.microedition.khronos.opengles.GL10


@Route(path = "/live/PublishActivity")
class PublishActivity : AppCompatActivity() {
    lateinit var binding:ActivityPublishBinding
    lateinit var mediaPublisher:MediaPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_publish)

    }

    override fun onResume() {
        super.onResume()
        mediaPublisher= MediaPublisher.newInstance(
            Config.Builder()
                .setFps(30)
                .setMaxWidth(720)
                .setMinWidth(360)
                .setUrl(BuildConfig.RTMP_URL)
                .build()
        )
        mediaPublisher.init()
        binding.surfaceView.setCallback(object  : OnSurfaceCallback{
            override fun surfaceCreated() {
                mediaPublisher.initVideoGatherer(this@PublishActivity,binding.surfaceView.surfaceTexture)
            }

            override fun surfaceChanged(gl: GL10?, width: Int, height: Int) {
                mediaPublisher.initVideoGatherer(this@PublishActivity,binding.surfaceView.surfaceTexture)

            }

        })
       binding.checkbox.setOnCheckedChangeListener{_,b->
            if(b){
                start()
            }else{
                stop()
            }
        }
    }
    fun start(){
        mediaPublisher.initAudioGatherer()
        mediaPublisher.initEncoders()
        mediaPublisher.startGather()
        mediaPublisher.startEncoder()
        mediaPublisher.starPublish()
    }
    fun stop(){
        mediaPublisher.stopPublish()
        mediaPublisher.stopEncoder()
        mediaPublisher.startGather()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPublisher.release()
    }
}