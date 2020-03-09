package com.example.zhihunews.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)      //连接超时
        .readTimeout(20, TimeUnit.SECONDS)       //读取超时
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://news-at.zhihu.com/api/4/news/")
        .client(client)
        //设置数据解析器
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    inline operator fun <reified T> invoke(): T = retrofit.create(T::class.java)
}