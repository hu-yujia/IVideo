package com.example.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.logDebug
import com.example.mine.databinding.ActivityLoginMainBinding
import com.example.mine.databinding.ActivityRegisterBinding
import com.example.mine.viewmodel.LoginIntent
import com.example.mine.viewmodel.LoginState
import com.example.mine.viewmodel.LoginViewModel
import com.example.mine.viewmodel.RegisterIntent
import com.example.mine.viewmodel.RegisterState
import com.example.mine.viewmodel.RegisterViewModel
import com.example.mvicore.BaseActivity
import com.example.network.user
import kotlinx.coroutines.launch

@Route(path = "/mine/RegisterActivity")

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    val userMap= ObservableArrayMap<String,String>().apply {
        put("username","")
        put("password","")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.user = userMap
        binding.back.setOnClickListener {
            finish()
        }
        binding.register.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intent.send(RegisterIntent.register(userMap["username"]?:"", userMap["password"]?:""))
            }
        }
    }
    fun succeed(response: RegisterState.Response){

        logDebug(response.user)
        finish()
    }
    fun fail(error: RegisterState.Error){
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
        logDebug(error.msg)
    }
}