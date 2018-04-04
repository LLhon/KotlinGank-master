package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.bean.TypeBean
import com.llhon.kotlingank.mvp.contract.HomeContract
import com.llhon.kotlingank.mvp.presenter.HomePresenter
import com.llhon.kotlingank.utils.loge
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/4/3 20:33
 * @description
 */
class HomeFragment : BaseLazyFragment(), HomeContract.View {

    var mPresenter: HomePresenter? = null

    companion object {
        val HOME = "HOME"
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        fab.show(object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onShown(fab: FloatingActionButton?) {
                super.onShown(fab)
                loge("HomeFragment", "fab show...")
            }
        })
        fab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                loge("HomeFragment", "fab hide...")
            }
        })
        fab.show()
        toolbar_layout.title = "2015/08/06"
    }

    fun initData() {
        mPresenter = HomePresenter(context!!)
        mPresenter?.attachView(this)
        mPresenter?.requestData("2015/08/06")
    }

    override fun showSuccessView(data: TypeBean) {
        if (data.福利 != null && data.福利.isNotEmpty()) {
            Glide.with(context)
                    .load(data.福利[0].url)
                    .centerCrop()
                    .crossFade()
                    .into(iv)
        }
        if (data.Android != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "Android"
            addSubTitleView(ll_subtitle, data.Android)
            ll_content.addView(view)
        }
        if (data.iOS != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "IOS"
            addSubTitleView(ll_subtitle, data.iOS)
            ll_content.addView(view)
        }
        if (data.App != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "App"
            addSubTitleView(ll_subtitle, data.App)
            ll_content.addView(view)
        }
        if (data.前端 != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "前端"
            addSubTitleView(ll_subtitle, data.前端)
            ll_content.addView(view)
        }
        if (data.拓展资源 != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "拓展资源"
            addSubTitleView(ll_subtitle, data.拓展资源)
            ll_content.addView(view)
        }
        if (data.瞎推荐 != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "瞎推荐"
            addSubTitleView(ll_subtitle, data.瞎推荐)
            ll_content.addView(view)
        }
        if (data.休息视频 != null) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
            val tv_title = view.findViewById<TextView>(R.id.tv_title)
            val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
            tv_title.text = "休息视频"
            addSubTitleView(ll_subtitle, data.休息视频)
            ll_content.addView(view)
        }
    }

    override fun showErrorView() {

    }

    fun addSubTitleView(ll_subtitle: LinearLayout, datas: List<GankBean>) {
        for (item in datas) {
            val tv = TextView(context)
            tv.text = item.desc + "(${item.who})"
            ll_subtitle.addView(tv)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
    }
}