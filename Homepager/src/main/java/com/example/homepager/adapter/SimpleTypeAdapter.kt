package com.example.homepager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.homepager.model.SimpleType
import com.example.homepager.ui.FollowFragment
import com.example.homepager.ui.RecommendFragment
import com.example.homepager.ui.SimpleTypeFragment

class SimpleTypeAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    private var types:MutableList<SimpleType> = mutableListOf()
    override fun getCount(): Int {
        return types.size + 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->FollowFragment.newInstance()
            1->RecommendFragment.newInstance()
            else->SimpleTypeFragment.newInstance(types[position-2].typename)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0->"关注"
            1->"推荐"
            else->types[position-2].typename
        }
    }
    operator fun plusAssign(list:List<SimpleType>){
        types+=list
        notifyDataSetChanged()
    }
}