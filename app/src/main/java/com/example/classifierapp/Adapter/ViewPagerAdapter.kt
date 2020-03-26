package com.example.classifierapp.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(fm: FragmentManager, behaviour: Int) : FragmentPagerAdapter(fm, behaviour) {
    var fragmentList: ArrayList<Fragment> = arrayListOf<Fragment>()
    var pageTitle = arrayListOf<String>()
    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitle.get(position)
    }

    public fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        pageTitle.add(title)
    }

}