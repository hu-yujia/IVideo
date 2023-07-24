package com.example.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.mine.databinding.ActivityLoginMainBinding

@Route(path = "/mine/LoginMainActivity")
class LoginMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginMainBinding>(this,R.layout.activity_login_main)
    }
}