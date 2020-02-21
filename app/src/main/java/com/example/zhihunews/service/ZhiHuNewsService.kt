package com.example.zhihunews.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ZhiHuNewsService {
    @GET("latest")
    fun loadData():Call<ZhiHuNewsBean>

    @GET("before/{id}")
    fun loadMoreData(@Path("id") id :String):Call<ZhiHuNewsBean>
}




