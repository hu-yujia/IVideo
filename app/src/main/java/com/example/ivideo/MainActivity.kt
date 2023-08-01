package com.example.ivideo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.example.ivideo.databinding.ActivityMainBinding
import com.example.ivideo.viewmodel.MainIntent
import com.example.ivideo.viewmodel.MainState
import com.example.ivideo.viewmodel.MainViewModel
import com.example.mvicore.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.security.Permissions

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val permission= arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    var flag=false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(MainIntent.Start(3))
        }
        binding.version.text = "版本: ${BuildConfig.VERSION_NAME}"
        flag=permission.map { checkSelfPermission(it) }
            .map { it== PackageManager.PERMISSION_GRANTED}.reduce{ a, b->a&&b}
        if(!flag){
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
                it.values.forEach { b->
                    if(b){
                        finish()
                    }
                }
                jumpActivity()
            }.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
        }

    }
    fun progress(response: MainState.Progress) {
        binding.timer.text = String.format("倒计时%2d", response.v)
    }
    fun complete(finish: MainState.Finish) {
        if(flag){
            jumpActivity()
        }
    }
    fun jumpActivity(){
        ARouter.getInstance()
            .build("/homemodel/home")
            .navigation()
        finish()
    }
}