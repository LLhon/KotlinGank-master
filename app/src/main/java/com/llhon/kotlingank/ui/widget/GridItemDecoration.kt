package com.llhon.kotlingank.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.llhon.kotlingank.utils.loge

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.widget
 * @author LLhon
 * @Date 2018/3/29 11:28
 * @description RecyclerView布局为GridLayoutManager设置item间距
 */
class GridItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    val ATTRS: IntArray = intArrayOf(android.R.attr.listDivider)
    var mDivider: Drawable? = null

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = typedArray.getDrawable(0)
        typedArray.recycle()
    }

    constructor(context: Context, drawableId: Int) : this(context) {
        mDivider = ContextCompat.getDrawable(context, drawableId)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    fun drawHorizontal(canvas: Canvas?, parent: RecyclerView?) {
        val childCount: Int? = parent?.childCount
        for (i in 0..childCount!!) {
            loge("GridItemDecoration", "child: $i")
            val child: View? = parent?.getChildAt(i)
            child?.layoutParams?.let {
                val params: RecyclerView.LayoutParams = child?.layoutParams as RecyclerView.LayoutParams
                val left = child.left - params.leftMargin
                val right = child.right + params.rightMargin + mDivider?.intrinsicWidth!!
                val top = child.bottom + params.bottomMargin
                val bottom = child.top + mDivider?.intrinsicHeight!!
                mDivider?.setBounds(left, top, right, bottom)
                mDivider?.draw(canvas)
            }
        }
    }

    fun drawVertical(canvas: Canvas?, parent: RecyclerView?) {
        val childCount: Int? = parent?.childCount
        for (i in 0..childCount!!) {
            val child: View? = parent?.getChildAt(i)
            child?.layoutParams?.let {
                val params: RecyclerView.LayoutParams = child?.layoutParams as RecyclerView.LayoutParams
                val left = child.right + params.rightMargin
                val right = child.left + mDivider?.intrinsicWidth!!
                val top = child.top - params.topMargin
                val bottom = child.bottom + params.bottomMargin
                mDivider?.setBounds(left, top, right, bottom)
                mDivider?.draw(canvas)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
        super.getItemOffsets(outRect, itemPosition, parent)
        var spanCount = getSpanCount(parent)
        var childCount = parent?.adapter?.itemCount
        if (isLastRaw(parent, itemPosition, spanCount, childCount!!)) {
            //如果是最后一行,则不需要绘制底部
            outRect?.set(0, 0, mDivider?.intrinsicWidth!!, 0)
        } else if (isLastColum(parent, itemPosition, spanCount, childCount!!)) {
            //如果是最后一列,则不需要绘制右边
            outRect?.set(0, 0, 0, mDivider?.intrinsicHeight!!)
        } else {
            outRect?.set(0, 0, mDivider?.intrinsicWidth!!, mDivider?.intrinsicHeight!!)
        }
    }

    fun getSpanCount(parent: RecyclerView?): Int {
        //列数
        var spanCount = -1
        var layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    fun isLastColum(parent: RecyclerView?, position: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((position + 1) % spanCount == 0) {
                //如果是最后一列, 则不需要绘制右边
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((position + 1) % spanCount == 0) {
                    //如果是最后一列,则不需要绘制右边
                    return true
                }
            } else {
                var childCount = childCount - childCount % spanCount
                if (position >= childCount) {
                    //如果是最后一列,则不需要绘制右边
                    return true
                }
            }
        }
        return false
    }

    fun isLastRaw(parent: RecyclerView?, position: Int, spanCount: Int, childCount: Int) : Boolean {
        val layoutManager = parent?.layoutManager
        if (layoutManager is GridLayoutManager) {
            var childCount = childCount - childCount % spanCount
            if (position >= childCount) {
                //如果是最后一行,则不需要绘制底部
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                //纵向滚动
                var childCount = childCount - childCount % spanCount
                if (position >= childCount) return true
            } else {
                //横向滚动
                if ((position + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }
}