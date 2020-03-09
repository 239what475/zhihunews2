package com.example.zhihunews.service

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface ZhiHuNewsService {
    @GET("latest")
    fun loadData():Deferred<ZhiHuNewsBean>

    @GET("before/{id}")
    fun loadMoreData(@Path("id") id :String):Deferred<ZhiHuNewsBean>

    companion object : ZhiHuNewsService by ServiceFactory()
}




