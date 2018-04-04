package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
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
import com.llhon.kotlingank.utils.UIUtils
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
            addContentView("Android", data.Android)
        }
        if (data.iOS != null) {
            addContentView("IOS", data.iOS)
        }
        if (data.App != null) {
            addContentView("App", data.App)
        }
        if (data.前端 != null) {
            addContentView("前端", data.前端)
        }
        if (data.拓展资源 != null) {
            addContentView("拓展资源", data.拓展资源)
        }
        if (data.瞎推荐 != null) {
            addContentView("瞎推荐", data.瞎推荐)
        }
        if (data.休息视频 != null) {
            addContentView("休息视频", data.休息视频)
        }
    }

    override fun showErrorView() {

    }

    fun addContentView(title: String, datas: List<GankBean>) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
        tv_title.text = title
        addSubTitleView(ll_subtitle, datas)
        ll_content.addView(view)
    }

    fun addSubTitleView(ll_subtitle: LinearLayout, datas: List<GankBean>) {
        for (item in datas) {
            val tv = TextView(context)
            tv.setTextColor(ContextCompat.getColor(context!!, R.color.ff999999))
            tv.setPadding(UIUtils.dip2px(context!!, 5f), UIUtils.dip2px(context!!, 5f),
                    UIUtils.dip2px(context!!, 5f), UIUtils.dip2px(context!!, 5f))
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