package com.llhon.kotlingank.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.activity
 * @author LLhon
 * @Date 2018/3/22 19:31
 * @description
 */
open abstract class BaseActivity : AppCompatActivity() {

    private val mDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(getLayoutId())
        initView()
        initData()
    }

    protected abstract fun getLayoutId(): Int

    open fun init() {

    }

    open fun initView() {

    }

    open fun initData() {

    }

    fun subscribe(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    fun unsubscribe() {
        if (mDisposables != null && !mDisposables.isDisposed) {
            mDisposables.dispose()
            mDisposables.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribe()
    }
}