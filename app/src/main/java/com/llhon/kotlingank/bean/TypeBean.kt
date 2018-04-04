package com.llhon.kotlingank.bean

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.bean
 * @author LLhon
 * @Date 2018/4/4 17:55
 * @description
 */
data class TypeBean(
        val Android: List<GankBean>,
        val iOS: List<GankBean>,
        val App: List<GankBean>,
        val 前端: List<GankBean>,
        val 瞎推荐: List<GankBean>,
        val 拓展资源: List<GankBean>,
        val 福利: List<GankBean>,
        val 休息视频: List<GankBean>
)