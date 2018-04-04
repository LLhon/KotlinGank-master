package com.llhon.kotlingank.ui.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.widget
 * @author LLhon
 * @Date 2018/3/29 18:21
 * @description
 */
class NoScrollViewPager : ViewPager {

    private val isPagingEnabled = false

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return this.isPagingEnabled && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return this.isPagingEnabled && super.onInterceptTouchEvent(ev)
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }
}