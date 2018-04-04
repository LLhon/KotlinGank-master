package com.llhon.kotlingank.net

import android.content.Context
import android.util.Log
import com.llhon.kotlingank.utils.UIUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.net
 * @author LLhon
 * @Date 2018/3/27 11:12
 * @description
 */
class CacheInterceptor(context: Context) : Interceptor {

    val mContext: Context = context

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        if (UIUtils.isNetConnected(mContext)) {
            val response = chain?.proceed(request)
            val maxAge = 60
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain?.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
        }
    }
}