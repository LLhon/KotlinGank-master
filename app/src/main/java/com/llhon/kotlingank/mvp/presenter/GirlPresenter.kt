package com.llhon.kotlingank.mvp.presenter

import android.content.Context
import com.llhon.kotlingank.base.BasePresenter
import com.llhon.kotlingank.mvp.contract.GirlContract
import com.llhon.kotlingank.net.ApiService
import com.llhon.kotlingank.net.RetrofitClient
import com.llhon.kotlingank.utils.applySchedulers

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.presenter
 * @author LLhon
 * @Date 2018/3/30 11:01
 * @description
 */
class GirlPresenter(context: Context) : BasePresenter<GirlContract.View>(), GirlContract.Presenter {

    init {
        mContext = context
    }

    override fun requestData(curPage: Int) {
        var apiService = RetrofitClient.getInstance(mContext, ApiService.BASE_URL).create(ApiService::class.java) as ApiService
        apiService.getGirlData(curPage)
                .applySchedulers()
                .subscribe({ result ->
                    if (!result.error) {
                        mvpView.showSuccessView(result.results)
                    } else {
                        mvpView.showErrorView()
                    }
                }, { _ ->
                    mvpView.showErrorView()
                }, {

                }, { d ->
                    subscribe(d)
                })
    }
}