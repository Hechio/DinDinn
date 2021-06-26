package com.stevehechio.apps.dindinnassigment.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * Created by stevehechio on 6/26/21
 */
class SectionPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    //this method is use to add as many fragments as available e.g
    //based on how many menu categories we have
    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}