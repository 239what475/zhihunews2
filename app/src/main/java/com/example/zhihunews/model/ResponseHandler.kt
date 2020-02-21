package com.example.zhihunews.model

import com.example.zhihunews.service.ZhiHuNewsBean

interface ResponseHandler {
    fun onError(msg:String?)
    fun onSuccess(type:Int,result:ZhiHuNewsBean)
}

class ZhiHuNewsRequest (val type:Int,val handler: ResponseHandler)