package com.example.zhihunews.model

import com.example.zhihunews.ThreadUtil
import com.example.zhihunews.service.ServiceFactory
import com.example.zhihunews.service.ZhiHuNewsBean
import com.example.zhihunews.service.ZhiHuNewsService
import retrofit2.Call
import retrofit2.Response

class NetManager private constructor(){
    companion object{
        val manager by lazy { NetManager() }
    }

    fun sendRequest(request: ZhiHuNewsRequest){
        val service = ServiceFactory.retrofit.create(ZhiHuNewsService::class.java).loadData()
        furtherProcessing(service,request)
    }

    fun loadMore(request: ZhiHuNewsRequest,id:String){
        val service = ServiceFactory.retrofit.create(ZhiHuNewsService::class.java).loadMoreData(id)
        furtherProcessing(service,request)
    }

    private fun furtherProcessing(call : Call<ZhiHuNewsBean>, request: ZhiHuNewsRequest){
        call.enqueue(object :retrofit2.Callback<ZhiHuNewsBean>{
            override fun onFailure(call: Call<ZhiHuNewsBean>, t: Throwable) {
                ThreadUtil.runOnMainThread(Runnable { request.handler.onError(t.message) })
            }

            override fun onResponse(call: Call<ZhiHuNewsBean>, response: Response<ZhiHuNewsBean>) {
                ThreadUtil.runOnMainThread(Runnable { request.handler.onSuccess(request.type,response.body()!!)})
            }
        })
    }
}