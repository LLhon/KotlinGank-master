package com.llhon.kotlingank.mvp.contract

import com.llhon.kotlingank.base.BaseView
import com.llhon.kotlingank.bean.GankBean

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.mvp.contract
 * @author LLhon
 * @Date 2018/3/30 11:01
 * @description
 */
class GirlContract {

    interface Presenter {
        fun requestData(curPage: Int)
    }

    interface View : BaseView {
        fun showSuccessView(datas: MutableList<GankBean>)
        fun showErrorView()
    }
}