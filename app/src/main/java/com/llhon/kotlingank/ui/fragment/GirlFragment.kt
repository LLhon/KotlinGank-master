package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.kennyc.view.MultiStateView
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.mvp.contract.GirlContract
import com.llhon.kotlingank.mvp.presenter.GirlPresenter
import com.llhon.kotlingank.ui.adapter.GirlAdapter
import com.llhon.kotlingank.utils.loge
import kotlinx.android.synthetic.main.fragment_girl.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/3/26 15:43
 * @description
 */
class GirlFragment : BaseLazyFragment(), GirlContract.View {

    var mAdapter: GirlAdapter? = null
    var mDatas: MutableList<GankBean> = ArrayList()
    var mCurPage: Int = 1
    var mPresenter: GirlPresenter? = null

    companion object {
        val GIRL = "GIRL"
        fun newInstance(): GirlFragment {
            val fragment = GirlFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_girl

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        loge("GridFragment", "initView()")
        multiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
        multiStateView.getView(MultiStateView.VIEW_STATE_ERROR)?.findViewById<View>(R.id.retry)
                ?.setOnClickListener {
                    multiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
                    initData()
                }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setHasFixedSize(true)
//        recyclerView.addItemDecoration(GridItemDecoration(context, R.drawable.item_divider_grid_white))
        mAdapter = GirlAdapter(context!!, mDatas)
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!!) {
                    mCurPage++
                    mPresenter?.requestData(mCurPage)
                }
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
        mPresenter = GirlPresenter(context!!)
        mPresenter?.attachView(this)
    }

    fun initData() {
        mCurPage = 1
        mPresenter?.requestData(mCurPage)
    }

    override fun showSuccessView(datas: MutableList<GankBean>) {
        swipeRefreshLayout.isRefreshing = false
        multiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        if (mCurPage == 1) {
            mDatas.clear()
        }
        mDatas.addAll(datas)
        mAdapter?.notifyDataSetChanged()
        if (mDatas.size == 0) {
            multiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun showErrorView() {
        swipeRefreshLayout.isRefreshing = false
        multiStateView.viewState = MultiStateView.VIEW_STATE_ERROR
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
    }
}