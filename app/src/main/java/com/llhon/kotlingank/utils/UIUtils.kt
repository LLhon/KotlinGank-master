package com.llhon.kotlingank.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.utils
 * @author LLhon
 * @Date 2018/3/27 11:14
 * @description
 */
object UIUtils {

    fun dip2px(context: Context, dipValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return dipValue * scale + 0.5f
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun isNetConnected(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo ?= connectivityManager.activeNetworkInfo
        if (networkInfo == null) {
            return false
        } else {
            return networkInfo.isAvailable && networkInfo.isConnected
        }
    }
}