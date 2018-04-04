package com.llhon.kotlingank.mvp.presenter

import android.content.Context
import android.widget.Toast
import com.llhon.kotlingank.base.BasePresenter
import com.llhon.kotlingank.bean.HttpResult
import com.llhon.kotlingank.bean.TypeBean
import com.llhon.kotlingank.mvp.contract.HomeContract
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
 * @Date 2018/4/3 20:36
 * @description
 */
class HomePresenter(context: Context) : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    init {
        mContext = context
    }

    override fun requestData(date: String) {
        val apiService = RetrofitClient.getInstance(mContext, ApiService.BASE_URL).create(ApiService::class.java) as ApiService
//        apiService?.getAndroidData(mCurPage)?.applySchedulers()?.subscribe { httpResult ->
//            if (!httpResult.error) {
//                mDatas.addAll(httpResult.results)
//                mAdapter?.notifyDataSetChanged()
//            }
//        }
        apiService?.getDateData(date)?.applySchedulers()?.subscribe(object : Observer<HttpResult<TypeBean>> {

            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                subscribe(d)
            }

            override fun onNext(t: HttpResult<TypeBean>) {
                loge("HomePresenter", "onNext()")
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