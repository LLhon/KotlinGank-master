package com.llhon.kotlingank.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.activity
 * @author LLhon
 * @Date 2018/3/22 19:31
 * @description
 */
open abstract class BaseActivity : AppCompatActivity() {

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

    override fun onDestroy() {
        super.onDestroy()
    }
}