package com.llhon.kotlingank.net

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.llhon.kotlingank.utils.loge
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.net
 * @author LLhon
 * @Date 2018/3/27 10:59
 * @description
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {

    val mContext: Context = context
    var mRetrofit: Retrofit? = null
    var mOkhttpClient: OkHttpClient? = null
    val mUrl = baseUrl
    var mCacheDirectory: File? = null
    var mCache: Cache? = null
    val DEFAULT_TIMEOUT: Long = 20

    init {
        if (mCacheDirectory == null) {
            mCacheDirectory = File(mContext.cacheDir,"app_cache")
        }
        try {
            if (mCache == null) {
                mCache = Cache(mCacheDirectory, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            loge("Okhttp", "Could not create http cache")
        }
        mOkhttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(mCache)
                .addInterceptor(CacheInterceptor(mContext))
                .addNetworkInterceptor(CacheInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        mRetrofit = Retrofit.Builder()
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mUrl)
                .build()
    }

    companion object {
        @Volatile
        var instance: RetrofitClient?= null

        fun getInstance(context: Context, baseUrl: String) : RetrofitClient {
            if (instance == null) {
                synchronized (RetrofitClient::class) {
                    if (instance == null) {
                        instance = RetrofitClient(context, baseUrl)
                    }
                }
            }
            return instance!!
        }
    }

    fun <T> create(service: Class<T>?) : T? {
        if (service == null) {
            throw RuntimeException("Api Service is null!")
        }
        return mRetrofit?.create(service)
    }
}