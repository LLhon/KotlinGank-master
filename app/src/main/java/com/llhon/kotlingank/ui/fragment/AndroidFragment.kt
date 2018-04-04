package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.mvp.contract.AndroidContract
import com.llhon.kotlingank.mvp.presenter.AndroidPresenter
import com.llhon.kotlingank.ui.adapter.AndroidAdapter
import com.llhon.kotlingank.utils.loge
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_android.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/3/26 15:43
 * @description
 */
class AndroidFragment : BaseLazyFragment(), AndroidContract.View {

    var mDatas: MutableList<GankBean> = ArrayList()
    var mAdapter: AndroidAdapter? = null
    var mCurPage: Int = 1
    var mContinueLoad: Boolean = true
    var mLoadMoreWrapper: LoadMoreWrapper<View>? = null
    var mPresenter: AndroidPresenter? = null

    companion object {
        val ANDROID = "ANDROID"
        fun newInstance(): AndroidFragment {
            val fragment = AndroidFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_android

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    private fun initView() {
        loge("AndroidFragment", "initView()")
        swipeRefreshLayout.setOnRefreshListener {
            mCurPage = 1
            mContinueLoad = true
            mPresenter?.requestData(mCurPage)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.addItemDecoration(DividerItemDecoration(context,
//                DividerItemDecoration.VERTICAL_LIST, false))
        mAdapter = AndroidAdapter(context!!, mDatas)
//        mLoadMoreWrapper = LoadMoreWrapper<View>(mAdapter)
//        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(context).inflate(R.layout.layout_load_more, null, false))
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mDatas.size - 1) {
                    mCurPage++
                    mPresenter?.requestData(mCurPage)
                }
            }
        })
        mPresenter = AndroidPresenter(context!!)
        mPresenter?.attachView(this)
    }

    private fun initData() {
        mPresenter?.requestData(mCurPage)
    }

    override fun showSuccessView(datas: MutableList<GankBean>) {
        swipeRefreshLayout.isRefreshing = false
        if (mCurPage == 1) {
            mDatas.clear()
        }
        mDatas.addAll(datas)
        mAdapter?.notifyDataSetChanged()
    }

    override fun showErrorView() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
    }
}