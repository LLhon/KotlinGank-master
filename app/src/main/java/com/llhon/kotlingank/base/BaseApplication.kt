package com.llhon.kotlingank.base

import android.app.Application
import com.llhon.kotlingank.utils.loge

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.base
 * @author LLhon
 * @Date 2018/3/23 17:13
 * @description
 */
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        loge("BaseApplication", "onCreate()")
    }
}