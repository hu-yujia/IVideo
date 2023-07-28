package com.example.homepager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.homepager.model.SimpleType
import com.example.homepager.model.VideoModel
import com.example.homepager.ui.CommentFragment
import com.example.homepager.ui.DescFragment
import com.example.homepager.ui.FollowFragment
import com.example.homepager.ui.RecommendFragment
import com.example.homepager.ui.SimpleTypeFragment

class DetialsAdapter(fragmentManager: FragmentManager,val id:Int):FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->DescFragment.newInstance(id)
            1->CommentFragment.newInstance(id)
            else->DescFragment.newInstance(id)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0->"简介"
            1->"评论"

            else -> {""}
        }
    }

}