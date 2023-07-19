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
        binding.tv.setOnClickListener{
            lifecycleScope.launch {
                viewModel.intent.send(MainIntent.GetSimpleVideo)
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect{
                when (it){
                    is MainState.Error-> Toast.makeText(this@MainActivity, ""+it.msg ,Toast.LENGTH_SHORT).show()
                    is MainState.Response->binding.tv.text=it.data[0].description
                    else->{}
                }
            }
        }


    }
}