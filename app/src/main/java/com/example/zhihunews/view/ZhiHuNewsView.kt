package com.example.zhihunews.view

import com.example.zhihunews.service.ZhiHuNewsBean
import retrofit2.Response

interface ZhiHuNewsView {
    fun onError(message: String?)
    fun loadSuccess(list: ZhiHuNewsBean,counter:Int)
    fun loadMoreSuccess(list:ZhiHuNewsBean,counter: Int)
}