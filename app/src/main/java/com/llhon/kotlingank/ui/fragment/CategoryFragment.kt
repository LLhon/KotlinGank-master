package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.utils.loge
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/3/26 15:43
 * @description
 */
class CategoryFragment : BaseLazyFragment() {

    private lateinit var mTitles: Array<String>
    private lateinit var mFragments: MutableList<Fragment>

    companion object {
        val IOS = "IOS"
        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        loge("CategoryFragment", "initView()")
        mTitles = resources.getStringArray(R.array.TypeArray)
        for (item in mTitles) {
            var tab: TabLayout.Tab = tabLayout.newTab()
            tab.text = item
            tabLayout.addTab(tab)
        }
        mFragments = ArrayList()
        for (i in mTitles.indices) {
            mFragments.add(TypeFragment.newInstance(i))
        }
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {

            override fun getCount(): Int = mFragments.size

            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getPageTitle(position: Int): CharSequence = mTitles[position]
        }
    }

    fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}