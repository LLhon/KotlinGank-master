package com.llhon.kotlingank.bean

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.bean
 * @author LLhon
 * @Date 2018/3/27 10:02
 * @description
 */
class HttpResult<T>(val error: Boolean, val category: List<String>,
                         val results: T)