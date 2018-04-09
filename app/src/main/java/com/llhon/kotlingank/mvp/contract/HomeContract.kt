package com.llhon.kotlingank.mvp.contract

import com.llhon.kotlingank.base.BaseView
import com.llhon.kotlingank.bean.TypeBean

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.contract
 * @author LLhon
 * @Date 2018/4/3 20:35
 * @description
 */
class HomeContract {

    interface Presenter {
        fun requestData()
    }

    interface View : BaseView {
        fun showSuccessView(date: String, data: TypeBean)
        fun showErrorView()
    }
}