package com.llhon.kotlingank.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.llhon.kotlingank.R
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.constant.Constant
import com.llhon.kotlingank.ui.activity.WebViewActivity

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.adapter
 * @author LLhon
 * @Date 2018/4/2 11:52
 * @description
 */
class TypeAdapter(context: Context, datas: MutableList<GankBean>?) : RecyclerView.Adapter<TypeAdapter.TypeHolder>() {

    var mContext: Context? = null
    var mDatas: MutableList<GankBean>? = null
    var mInflater: LayoutInflater? = null

    //初始化
    init {
        mContext = context
        mDatas = datas
        mInflater = LayoutInflater.from(mContext)
    }

    override fun getItemCount(): Int {
        //?: 如果左侧的值为空就取右侧的值
        return mDatas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeHolder {
        //!! 非空断言操作符,可以将任何值转换为非空类型,若该值为空则抛出异常
        return TypeHolder(mContext!!, mInflater?.inflate(R.layout.item_type, parent, false))
    }

    override fun onBindViewHolder(holder: TypeHolder, position: Int) {
        var bean = mDatas?.get(position)
        holder.tvTitle?.text = bean?.desc
        holder.tvAuthor?.text = bean?.publishDate() + "  " + bean?.who
        if (bean?.images == null) {
            holder.fl_image?.visibility = View.GONE
        } else {
            holder.fl_image?.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(bean?.images?.get(0) + Constant.LIMIT_IMAGE_URL)
//                    .load(bean?.images?.get(0))
                    .asGif()
                    .fitCenter()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.iv)
        }
        holder.itemView?.setOnClickListener {
            var intent = Intent(mContext, WebViewActivity::class.java)
            intent.putExtra("url", bean?.url)
            mContext?.startActivity(intent)
        }
    }

    class TypeHolder(context: Context, itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView? = null
        var iv: ImageView? = null
        var tvAuthor: TextView? = null
        var fl_image: FrameLayout? = null

        init {
            //as 类型强转
            tvTitle = itemView?.findViewById(R.id.tv_title) as TextView?
            tvAuthor = itemView?.findViewById(R.id.tv_author)
            iv = itemView?.findViewById(R.id.iv)
            fl_image = itemView?.findViewById(R.id.fl_image)
        }
    }
}