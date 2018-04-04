package com.llhon.kotlingank.base

import android.os.Bundle

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.base
 * @author LLhon
 * @Date 2018/3/29 19:46
 * @description 懒加载fragment
 */
abstract class BaseLazyFragment : BaseFragment() {

    var isPrepared: Boolean = false
    protected var isFirst: Boolean = false
    var isFirstResume: Boolean = true
    var isFirstVisible: Boolean = true
    var isFirstInVisible: Boolean = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepared()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepared()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInVisible) {
                isFirstInVisible = false
                onFirstUserInVisible()
            } else {
                onUserInVisible()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInVisible()
        }
    }

    private fun initPrepared() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    /**
     * fragment可见(切换回来或onResume)
     */
    open protected fun onUserVisible() {

    }

    /**
     * fragment不可见(切换掉或onPause)
     */
    open protected fun onUserInVisible() {

    }

    /**
     * 第一次fragment可见(进行初始化工作)
     */
    open protected fun onFirstUserVisible() {

    }

    /**
     * 第一次fragment不可见(不建议在此处理事件)
     */
    open protected fun onFirstUserInVisible() {

    }
}