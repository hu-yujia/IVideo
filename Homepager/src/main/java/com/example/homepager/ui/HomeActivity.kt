package com.example.homepager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.homepager.R
import com.example.homepager.databinding.ActivityHomeBinding
import com.example.homepager.viewmodel.HomeViewModel
import com.example.live.LiveRoomFragment
import com.example.mine.MineFragment
import com.example.mvicore.BaseActivity
import com.example.network.user

@Route(path = "/homemodel/home")
class HomeActivity : BaseActivity<ActivityHomeBinding,HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, HomepageFragment.newInstance())
            .commit()
        binding.nav.setOnItemSelectedListener {
            if(it.itemId==R.id.home_mine&&user==null){
                ARouter.getInstance()
                    .build("/mine/LoginMainActivity")
                    .navigation()
                return@setOnItemSelectedListener false
            }
            val fragment = supportFragmentManager.fragments[0]
            if(it.itemId!=when(fragment){
                is HomepageFragment->R.id.home_homepage
                    is MineFragment->R.id.home_mine
                    is LiveRoomFragment-> R.id.hoem_recording
                    else->R.id.home_homepage
            }){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content,
                        when(it.itemId){
                            R.id.home_homepage->HomepageFragment.newInstance()
                            R.id.hoem_recording->LiveRoomFragment.newInstance()
                            R.id.home_mine->MineFragment.newInstance()
                            else->HomepageFragment.newInstance()
                        })
                    .commit()
            }
//            if((it.itemId==R.id.home_homepage && fragment !is HomepageFragment) ||
//                (it.itemId==R.id.home_mine && fragment !is MineFragment)){
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.content,{
//                            when(it.itemId){
//                                R.id.home_homepage->HomepageFragment.newInstance()
//                                R.id.home_mine->MineFragment.newInstance()
//                                else->HomepageFragment.newInstance()
//                            }
//                        }
//
//                            .commit()
//                }
            true
        }
        supportFragmentManager.addFragmentOnAttachListener{_,fragment->
            val itemId=when(fragment){
                is HomepageFragment->R.id.home_homepage
                is LiveRoomFragment->R.id.hoem_recording
                is MineFragment->R.id.home_mine
                else->R.id.home_homepage
            }
            if(itemId!=binding.nav.selectedItemId){
                binding.nav.selectedItemId=itemId
            }
        }
    }
}









