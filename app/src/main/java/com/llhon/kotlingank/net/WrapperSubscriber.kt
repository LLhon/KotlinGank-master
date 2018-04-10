package com.llhon.kotlingank.net

import com.llhon.kotlingank.utils.loge
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.net
 * @author LLhon
 * @Date 2018/4/10 9:55
 * @description
 */
open abstract class WrapperSubscriber<T>() : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        loge("WrapperSubscriber", e.message)

    }

    override fun onComplete() {

    }
}