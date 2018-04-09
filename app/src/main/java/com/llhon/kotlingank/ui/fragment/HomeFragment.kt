package com.llhon.kotlingank.ui.fragment

import android.animation.Animator
import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.Target
import com.llhon.kotlingank.R
import com.llhon.kotlingank.base.BaseLazyFragment
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.bean.TypeBean
import com.llhon.kotlingank.mvp.contract.HomeContract
import com.llhon.kotlingank.mvp.presenter.HomePresenter
import com.llhon.kotlingank.ui.activity.WebViewActivity
import com.llhon.kotlingank.utils.UIUtils
import com.llhon.kotlingank.utils.loge
import com.llhon.kotlingank.utils.showSnackbar
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.ui.fragment
 * @author LLhon
 * @Date 2018/4/3 20:33
 * @description
 */
class HomeFragment : BaseLazyFragment(), HomeContract.View {

    var mPresenter: HomePresenter? = null
    var mRevealFlag: Boolean = false
    var mImageUrl: String = ""

    companion object {
        val HOME = "HOME"
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onFirstUserVisible() {
        if (!isFirst) {
            isFirst = true
            initView()
            initData()
        }
    }

    fun initView() {
        fab.setOnClickListener { launchRevealAnimation() }
        btn_save.setOnClickListener { saveImage() }
    }

    fun initData() {
        mPresenter = HomePresenter(context!!)
        mPresenter?.attachView(this)
        mPresenter?.requestData()
    }

    override fun showSuccessView(date: String, data: TypeBean) {
        toolbar_layout.title = date
        if (data.福利 != null && data.福利.isNotEmpty()) {
            mImageUrl = data.福利[0].url
            Glide.with(context)
                    .load(data.福利[0].url)
                    .centerCrop()
                    .crossFade()
                    .into(iv)
        }
        data.Android?.let {
            addContentView("Android", data.Android)
        }
        data.iOS?.let {
            addContentView("IOS", data.iOS)
        }
        data.App?.let {
            addContentView("App", data.App)
        }
        data.前端?.let {
            addContentView("前端", data.前端)
        }
        data.拓展资源?.let {
            addContentView("拓展资源", data.拓展资源)
        }
        data.瞎推荐?.let {
            addContentView("瞎推荐", data.瞎推荐)
        }
        data.休息视频?.let {
            addContentView("休息视频", data.休息视频)
        }
    }

    override fun showErrorView() {
        //TODO marvel marvelhon marvehon marvhong Marvhong Marvhon
    }

    private fun addContentView(title: String, datas: List<GankBean>) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_home_content, null, false)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val ll_subtitle = view.findViewById<LinearLayout>(R.id.ll_subtitle)
        tv_title.text = title
        addSubTitleView(ll_subtitle, datas)
        ll_content.addView(view)
    }

    private fun addSubTitleView(ll_subtitle: LinearLayout, datas: List<GankBean>) {
        for (item in datas) {
            val tv = TextView(context)
            tv.setTextColor(ContextCompat.getColor(context!!, R.color.ff999999))
            tv.setPadding(UIUtils.dip2px(context!!, 5f), UIUtils.dip2px(context!!, 5f),
                    UIUtils.dip2px(context!!, 5f), UIUtils.dip2px(context!!, 5f))
            tv.text = "• ${item.desc}(${item.who})"
            tv.setOnClickListener {
                WebViewActivity.startActivity(context!!, item.url)
            }
            ll_subtitle.addView(tv)
        }
    }

    /**
     * 开启揭露动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun launchRevealAnimation() {
        val animation = iv.animation
        animation?.cancel()
        //求出第2个和第3个参数
        var vLocation = IntArray(2)
        fab.getLocationInWindow(vLocation)
        var centerX = vLocation[0] + fab.width / 2
        var centerY = vLocation[1] + fab.height / 2

        //求出要揭露View的对角线,来作为扩散圆的最大半径
        var hypotenuse = Math.hypot(iv.width.toDouble(), iv.height.toDouble()).toFloat()
        if (mRevealFlag) {
            //隐藏 揭露对象
            loge("RevealAnimation", "reveal == true")
            val circularReveal = ViewAnimationUtils.createCircularReveal(iv, centerX, centerY, hypotenuse, 0f)
            circularReveal.duration = 1000
            circularReveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    iv.visibility = View.GONE
                    fl_save.visibility = View.VISIBLE
                    circularReveal.removeListener(this)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            fab.setImageResource(R.drawable.ic_close_black_24dp)
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context!!, android.R.color.white)))
            circularReveal.start()
            mRevealFlag = false
        } else {
            //显示 揭露对象
            loge("RevealAnimation", "reveal == false")
            val circularReveal = ViewAnimationUtils.createCircularReveal(iv, centerX, centerY, 0f, hypotenuse)
            circularReveal.duration = 1000
            circularReveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    circularReveal.removeListener(this)
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
            iv.visibility = View.VISIBLE
            fl_save.visibility = View.GONE
            fab.setImageResource(R.drawable.ic_remove_red_eye_white_24dp)
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context!!, android.R.color.black)))
            circularReveal.start()
            mRevealFlag = true
        }
    }

    /**
     * 保存图片到本地
     */
    private fun saveImage() {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                try {
                    val target: FutureTarget<File> = Glide.with(this@HomeFragment)
                            .load(mImageUrl)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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
                .subscribe({ t: String? ->
                    loge("PictureActivity", "image: $t")
                    showSnackbar(btn_save, "图片已保存到<$t>", Snackbar.LENGTH_INDEFINITE)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
    }
}