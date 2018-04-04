package com.llhon.kotlingank.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.presenter
 * @author LLhon
 * @Date 2018/3/20 20:25
 * @description
 */
open abstract class BasePresenter<T: BaseView>: Presenter<T> {

    lateinit var mvpView: T

    private val mDisposables = CompositeDisposable()

    protected lateinit var mContext: Context

    override fun attachView(view: T) {
        mvpView = view
    }

    override fun detachView() {
//        mvpView = null
        unsubscribe()
    }

    fun isAttachView(): Boolean = mvpView != null

    override fun subscribe(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    override fun unsubscribe() {
        onDestory()
    }

    open fun onDestory() {
        if (mDisposables != null && !mDisposables.isDisposed) {
            mDisposables.dispose()
            mDisposables.clear()
        }
    }
}