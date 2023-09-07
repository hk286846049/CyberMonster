package com.example.cybermonster.view


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragmentList: List<Fragment>) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    override fun containsItem(itemId: Long): Boolean {
        // 每次都返回false，以确保每次都重新加载Fragment
        return false
    }
}
