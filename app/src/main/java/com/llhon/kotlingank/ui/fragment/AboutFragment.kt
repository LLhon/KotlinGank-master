package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.ui.activity.WebViewActivity
import com.llhon.kotlingank.utils.loge
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/3/26 15:43
 * @description
 */
class AboutFragment : BaseLazyFragment() {

    companion object {
        val ABOUT = "ABOUT"
        fun newInstance(): AboutFragment {
            val fragment = AboutFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_about

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        loge("AboutFragment", "initView()")
        civ.setOnClickListener { _ ->
            WebViewActivity.startActivity(context!!, "https://github.com/LLhon")
        }
        //TODO 使用Lottie作动画
    }

    fun initData() {

    }
}