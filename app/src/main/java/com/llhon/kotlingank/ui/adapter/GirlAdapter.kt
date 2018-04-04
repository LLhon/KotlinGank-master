package com.llhon.kotlingank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.llhon.kotlingank.R
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.ui.activity.PictureActivity

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.adapter
 * @author LLhon
 * @Date 2018/3/28 20:04
 * @description
 */
class GirlAdapter(context: Context, datas: MutableList<GankBean>) : RecyclerView.Adapter<GirlAdapter.GirlHolder>() {

    var mContext: Context? = null
    var mDatas: MutableList<GankBean>? = null

    init {
        mContext = context
        mDatas = datas
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlHolder {
        return GirlHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.item_girl, parent, false))
    }

    override fun onBindViewHolder(holder: GirlHolder, position: Int) {
        var bean = mDatas?.get(position)
        Glide.with(mContext)
                .load(bean?.url)
                .crossFade()
                .centerCrop()
                .into(holder.iv_girl)
        holder.itemView?.setOnClickListener {
            PictureActivity.startActivity(mContext!!, holder.iv_girl!!, bean?.url!!)
        }
    }

    class GirlHolder(context: Context?, itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var iv_girl: ImageView? = null

        init {
            iv_girl = itemView?.findViewById(R.id.iv_girl)
            var layoutParams: ViewGroup.LayoutParams? = iv_girl?.layoutParams
            layoutParams?.height = (300 + Math.random() * 800).toInt()
            iv_girl?.layoutParams = layoutParams
        }
    }
}