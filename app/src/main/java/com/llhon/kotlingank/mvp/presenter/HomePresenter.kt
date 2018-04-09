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
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.presenter
 * @author LLhon
 * @Date 2018/4/3 20:36
 * @description
 */
class HomePresenter(context: Context) : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    var mDate: String = ""

    init {
        mContext = context
    }

    override fun requestData() {
        val apiService = RetrofitClient.getInstance(mContext, ApiService.BASE_URL).create(ApiService::class.java) as ApiService
//        apiService?.getAndroidData(mCurPage)?.applySchedulers()?.subscribe { httpResult ->
//            if (!httpResult.error) {
//                mDatas.addAll(httpResult.results)
//                mAdapter?.notifyDataSetChanged()
//            }
//        }
        apiService?.getHistoryData()?.flatMap(object : Function<HttpResult<MutableList<String>>, ObservableSource<HttpResult<TypeBean>>> {
            override fun apply(t: HttpResult<MutableList<String>>): ObservableSource<HttpResult<TypeBean>> {
                var date: String = t.results[0]
                mDate = date.replace("-", "/")
                return apiService?.getDateData(mDate)
            }
        })
                ?.applySchedulers()?.subscribe(object : Observer<HttpResult<TypeBean>> {

            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                subscribe(d)
            }

            override fun onNext(t: HttpResult<TypeBean>) {
                loge("HomePresenter", "onNext()")
                if (!t.error) {
                    mvpView.showSuccessView(mDate, t.results)
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