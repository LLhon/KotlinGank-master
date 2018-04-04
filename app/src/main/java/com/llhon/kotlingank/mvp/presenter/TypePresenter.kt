package com.llhon.kotlingank.mvp.presenter

import android.content.Context
import android.widget.Toast
import com.llhon.kotlingank.base.BasePresenter
import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.bean.HttpResult
import com.llhon.kotlingank.mvp.contract.TypeContract
import com.llhon.kotlingank.net.ApiService
import com.llhon.kotlingank.net.RetrofitClient
import com.llhon.kotlingank.utils.applySchedulers
import com.llhon.kotlingank.utils.loge
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.presenter
 * @author LLhon
 * @Date 2018/4/2 11:35
 * @description
 */
class TypePresenter(context: Context, type: String) : BasePresenter<TypeContract.View>(), TypeContract.Presenter {

    private val mType: String

    init {
        mContext = context
        mType = type
    }

    override fun requestData(curPage: Int) {
        val apiService = RetrofitClient.getInstance(mContext, ApiService.BASE_URL).create(ApiService::class.java) as ApiService
//        apiService?.getAndroidData(mCurPage)?.applySchedulers()?.subscribe { httpResult ->
//            if (!httpResult.error) {
//                mDatas.addAll(httpResult.results)
//                mAdapter?.notifyDataSetChanged()
//            }
//        }
        apiService?.getData(mType, curPage)?.applySchedulers()?.subscribe(object : Observer<HttpResult<MutableList<GankBean>>> {

            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                subscribe(d)
            }

            override fun onNext(t: HttpResult<MutableList<GankBean>>) {
                loge("TypeFragment", "onNext()")
                if (!t.error) {
                    mvpView.showSuccessView(t.results)
                } else {
                    mvpView.showErrorView()
                    Toast.makeText(mContext, "数据异常", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(e: Throwable) {
                mvpView.showErrorView()
            }
        })
    }
}