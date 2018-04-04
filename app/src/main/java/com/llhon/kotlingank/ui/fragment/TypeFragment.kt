package com.llhon.kotlingank.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.kennyc.view.MultiStateView
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.mvp.contract.TypeContract
import com.llhon.kotlingank.mvp.presenter.TypePresenter
import com.llhon.kotlingank.ui.adapter.TypeAdapter
import com.llhon.kotlingank.utils.loge
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper
import kotlinx.android.synthetic.main.fragment_type.*

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/4/2 10:32
 * @description
 */
class TypeFragment : BaseLazyFragment(), TypeContract.View {

    var mDatas: MutableList<GankBean> = ArrayList()
    var mAdapter: TypeAdapter? = null
    var mCurPage: Int = 1
    var mContinueLoad: Boolean = true
    var mLoadMoreWrapper: LoadMoreWrapper<View>? = null
    var mPresenter: TypePresenter? = null

    companion object {
        val INDEX = "index"
        fun newInstance(index: Int) : TypeFragment {
            val fragment = TypeFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_type

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        //TODO 下拉刷新使用SwipeToLoadLayout替换
        var index = arguments?.getInt(INDEX)
        val typeArray = resources.getStringArray(R.array.TypeArray)
        var type = typeArray[index!!]
        loge("TypeFragment", "initView: $index")
        multiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
        multiStateView.getView(MultiStateView.VIEW_STATE_ERROR)?.findViewById<View>(R.id.retry)
                ?.setOnClickListener {
                    multiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
                    initData()
                }
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.addItemDecoration(DividerItemDecoration(context,
//                DividerItemDecoration.VERTICAL_LIST, false))
        mAdapter = TypeAdapter(context!!, mDatas)
        mLoadMoreWrapper = LoadMoreWrapper<View>(mAdapter)
        mLoadMoreWrapper?.setLoadMoreView(LayoutInflater.from(context).inflate(R.layout.layout_load_more, null, false))
        recyclerView.adapter = mLoadMoreWrapper
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                var linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
//                var lastPosition = linearLayoutManager.findLastVisibleItemPosition()
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mDatas.size - 1) {
//                    mCurPage++
//                    mPresenter?.requestData(mCurPage)
//                }
//            }
//        })
        mLoadMoreWrapper?.setOnLoadMoreListener {
            if (mContinueLoad) {
                mCurPage++
                mPresenter?.requestData(mCurPage)
            }
        }
        mPresenter = TypePresenter(context!!, type)
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
        mContinueLoad = datas.size != 0
        mDatas.addAll(datas)
        mLoadMoreWrapper?.notifyDataSetChanged()
        if (mDatas.size == 0) {
            multiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun showErrorView() {
        mContinueLoad = true
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