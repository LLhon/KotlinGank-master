package com.llhon.kotlingank.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseActivity
import com.llhon.kotlingank.utils.loge
import com.llhon.kotlingank.utils.showSnackbar
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture.*
import java.io.File

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.activity
 * @author LLhon
 * @Date 2018/3/29 15:18
 * @description 图片预览界面
 */
class PictureActivity : BaseActivity() {

    var mImageUrl: String? = null

    companion object {
        val IMAGE = "IMAGE"
        fun startActivity(context: Context, view: ImageView, url: String) {
            val intent = Intent(context, PictureActivity::class.java)
            intent.putExtra(IMAGE, url)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity, view, "image").toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }
    override fun getLayoutId(): Int = R.layout.activity_picture

    override fun initView() {
        mImageUrl = intent.getStringExtra(IMAGE)
        initTransition()
        Glide.with(this)
                .load(mImageUrl)
                .dontAnimate()
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable?, glideAnimation: GlideAnimation<in GlideDrawable>?) {
                        iv.setImageDrawable(resource)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition()
                        }
                    }
                })
        ll_image.setOnClickListener {
            supportFinishAfterTransition()
        }
        ll_image.setOnLongClickListener {
            AlertDialog.Builder(this)
                    .setMessage("是否保存图片")
                    .setPositiveButton("是", { dialog, which ->
                        saveImage()
                        dialog?.dismiss()
                    })
                    .setNegativeButton("否", { dialog, which ->
                        dialog?.dismiss()
                    })
                    .create()
                    .show()
            return@setOnLongClickListener true
        }
    }

    override fun initData() {

    }

    private fun initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //延迟共享元素动画的执行,防止出现闪屏问题
            postponeEnterTransition()
        }
    }

    /**
     * 保存图片到本地
     */
    private fun saveImage() {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                try {
                    val target: FutureTarget<File> = Glide.with(this@PictureActivity)
                            .load(mImageUrl)
                            .downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    emitter.onNext(target.get()?.absolutePath!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.onError(e)
                }
                emitter.onComplete()
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: String? ->
                    loge("PictureActivity", "image: $t")
                    showSnackbar(iv, "图片已保存到<$t>", Snackbar.LENGTH_INDEFINITE)
                })
    }
}