package com.example.ivideo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.example.ivideo.databinding.ActivityMainBinding
import com.example.ivideo.viewmodel.MainIntent
import com.example.ivideo.viewmodel.MainState
import com.example.ivideo.viewmodel.MainViewModel
import com.example.mvicore.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(MainIntent.Start(3))
        }
        binding.version.text = "版本: ${BuildConfig.VERSION_NAME}"
    }
    fun progress(response: MainState.Progress) {
        binding.timer.text = String.format("倒计时%2d", response.v)
    }
    fun complete(finish: MainState.Finish) {
        ARouter.getInstance()
            .build("/homemodel/home")
            .navigation()
        finish()
    }
}