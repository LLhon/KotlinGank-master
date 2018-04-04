package com.llhon.kotlingank.utils

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui
 * @author LLhon
 * @Date 2018/3/20 20:09
 * @description 类扩展方法
 */
fun loge(tag: String, msg: String?) {
    Log.e(tag, msg ?: tag)
}

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun showSnackbar(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}

fun Context.inflate(@LayoutRes resource: Int): View =
        LayoutInflater.from(this).inflate(resource, null)

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}