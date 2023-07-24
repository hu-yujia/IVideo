package com.example.homepager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager.R
import com.example.homepager.databinding.ActivityHomeBinding
import com.example.homepager.viewmodel.HomeViewModel
import com.example.mvicore.BaseActivity

@Route(path = "/homemodel/home")
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, HomepageFragment.newInstance())
            .commit()
        binding.nav.setOnItemSelectedListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content,
                when(it.itemId){
                    else->HomepageFragment.newInstance()
                })
                .commit()
            true
        }
    }
}









