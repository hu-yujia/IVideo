package com.example.ivideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ivideo.databinding.ActivityMainBinding
import com.example.ivideo.viewmodel.MainIntent
import com.example.ivideo.viewmodel.MainState
import com.example.ivideo.viewmodel.MainViewModel
import com.example.mvicore.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        binding.tv.setOnClickListener{
//            lifecycleScope.launch {
//                viewModel.intent.send(MainIntent.GetSimpleVideo)
//            }
//        }
        lifecycleScope.launch {
            viewModel.intent.send(MainIntent.Start(10))
        }
        binding.version.text="版本：${BuildConfig.BUILD_TYPE}"
    }
    fun load(process:MainState.Progress){
//        binding.tv.text=response.data[0].description
          binding.timer.text=String.format("倒计时%2d",process.v)
    }
    fun error(finish:MainState.Finish){
//        Toast.makeText(this@MainActivity, ""+error.msg, Toast.LENGTH_SHORT).show()
        Toast.makeText(this@MainActivity, ""+finish.s, Toast.LENGTH_SHORT).show()
    }
}