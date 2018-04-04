package com.llhon.kotlingank.ui.activity

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseActivity
import com.llhon.kotlingank.ui.fragment.AboutFragment
import com.llhon.kotlingank.ui.fragment.AndroidFragment
import com.llhon.kotlingank.ui.fragment.CategoryFragment
import com.llhon.kotlingank.ui.fragment.GirlFragment
import com.llhon.kotlingank.utils.loge
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.activity
 * @author LLhon
 * @Date 2018/3/22 20:59
 * @description
 */
class MainActivity : BaseActivity() {

    lateinit var mFragments: MutableList<Fragment>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        loge("MainActivity", "initView()")
        initFragments()
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getCount(): Int = mFragments.size
        }
        viewPager.offscreenPageLimit = 4
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        navigation.setOnNavigationItemSelectedListener {item ->
//            var tab = 0
//            when (item.itemId) {
//                R.id.navigation_android -> tab = 0
//                R.id.navigation_ios -> tab = 1
//                R.id.navigation_girl -> tab = 2
//                R.id.navigation_setting -> tab = 3
//            }
//            viewPager.setCurrentItem(tab)
//            false
//        }
    }

    override fun initData() {

    }

    fun initFragments() {
        mFragments = ArrayList()
        mFragments.add(AndroidFragment.newInstance())
//        mFragments.add(HomeFragment.newInstance())
        mFragments.add(CategoryFragment.newInstance())
        mFragments.add(GirlFragment.newInstance())
        mFragments.add(AboutFragment.newInstance())
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var tab = 0
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_category -> {
                viewPager.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_girl -> {
                viewPager.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                viewPager.setCurrentItem(3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}