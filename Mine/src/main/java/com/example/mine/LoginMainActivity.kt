package com.example.mine


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.logDebug
import com.example.mine.databinding.ActivityLoginMainBinding
import com.example.mine.viewmodel.LoginIntent
import com.example.mine.viewmodel.LoginState
import com.example.mine.viewmodel.LoginViewModel
import com.example.mvicore.BaseActivity
import com.example.network.gson
import kotlinx.coroutines.launch
import com.example.network.user

@Route(path = "/mine/LoginMainActivity")
class LoginMainActivity : BaseActivity<ActivityLoginMainBinding,LoginViewModel>() {
    val userMap by lazy { ObservableArrayMap<String,String>().apply {
        put("username","")
        put("password","")
    }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = getSharedPreferences("login", MODE_PRIVATE)
        val username = preferences.getString("username", "")
        userMap["username"]=username
        binding.user = userMap
        binding.login.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intent.send(LoginIntent.Login(userMap["username"]?:"", userMap["password"]?:""))
            }
        }
        binding.register.setOnClickListener {
            ARouter.getInstance().build("/mine/RegisterActivity").navigation()
        }
        val intent = Intent(this, LoginMainActivity::class.java)

    }
    fun succeed(response:LoginState.Response){
        user=response.user
        val preferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        preferences.edit()
            .putString("user",gson.toJson(user)).apply()
        logDebug(user)
        finish()
    }
    fun fail(error:LoginState.Error){
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
        logDebug(error.msg)
    }
}