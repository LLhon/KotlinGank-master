package com.llhon.kotlingank.net

import com.llhon.kotlingank.bean.GankBean
import com.llhon.kotlingank.bean.HttpResult
import com.llhon.kotlingank.bean.TypeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Project KotlinGank-master
 * @Package com.llhon.kotlingank.net
 * @author LLhon
 * @Date 2018/3/26 10:22
 * @description
 */
interface ApiService {

   //http://gank.io/api/data/Android/20/1
    companion object {
        val BASE_URL: String
            get() = "http://gank.io/api/"
    }

    @GET("data/Android/20/{page}")
    fun getAndroidData(@Path("page") page: Int): Observable<HttpResult<MutableList<GankBean>>>

    @GET("data/IOS/20/{page}")
    fun getIOSData(@Path("page") page: Int): Observable<HttpResult<MutableList<GankBean>>>

    @GET("data/福利/20/{page}")
    fun getGirlData(@Path("page") page: Int): Observable<HttpResult<MutableList<GankBean>>>

    @GET("data/all/20/{page}")
    fun getAllData(@Path("page") page: Int): Observable<HttpResult<MutableList<GankBean>>>

    @GET("data/{type}/20/{page}")
    fun getData(@Path("type") type: String, @Path("page") page: Int)
            : Observable<HttpResult<MutableList<GankBean>>>

    @GET("day/{date}")
    fun getDateData(@Path("date") date: String) : Observable<HttpResult<TypeBean>>

    @GET("random/data/{type}/{count}")
    fun getRandomData(@Path("type") type: String, @Path("count") count: Int)
            : Observable<HttpResult<MutableList<GankBean>>>

    @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
    fun getSearchData(@Path("type") type: String, @Path("count") count: Int, @Path("page") page: Int)
            : Observable<HttpResult<MutableList<GankBean>>>
}