package com.llhon.kotlingank.base

import io.reactivex.disposables.Disposable

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.presenter
 * @author LLhon
 * @Date 2018/3/20 20:29
 * @description
 */
interface Presenter<in T>: BaseView {

    fun attachView(view: T)

    fun detachView()

    fun subscribe(disposable: Disposable)

    fun unsubscribe()
}