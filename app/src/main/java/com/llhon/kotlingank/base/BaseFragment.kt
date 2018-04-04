package com.llhon.kotlingank.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.base
 * @author LLhon
 * @Date 2018/3/26 15:53
 * @description
 */
open abstract class BaseFragment : Fragment() {

    var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initEvent()
    }

    abstract fun getLayoutId(): Int

    protected open fun initEvent() {

    }
}