package com.example.zhihunews.presenter

import com.example.zhihunews.model.NetManager
import com.example.zhihunews.model.ResponseHandler
import com.example.zhihunews.model.ZhiHuNewsRequest
import com.example.zhihunews.service.ZhiHuNewsBean
import com.example.zhihunews.view.ZhiHuNewsView


class ZhiHuNewsPresenterImpl(private val zhiHuNewsView: ZhiHuNewsView) : ZhiHuNewsPresenter,
    ResponseHandler {
    val INIT_OR_REFRESH = 1
    val LOADMORE = 2
     var daysCounter = 0
    override fun onError(msg: String?) {
        zhiHuNewsView.onError(msg)
    }

    override fun onSuccess(type: Int, result: ZhiHuNewsBean) {
        when (type) {
            INIT_OR_REFRESH -> {
                daysCounter++
                zhiHuNewsView.loadSuccess(result,daysCounter)
            }
            LOADMORE->{
                daysCounter++
                if (daysCounter == 2){
                    zhiHuNewsView.loadSuccess(result,daysCounter)
                }else{
                    zhiHuNewsView.loadMoreSuccess(result,daysCounter)
                }
            }
        }
    }

    override fun loadData() {
        val request = ZhiHuNewsRequest(INIT_OR_REFRESH, this)
        NetManager.manager.sendRequest(request)
    }

    override fun loadMore(id: String) {
        val request = ZhiHuNewsRequest(LOADMORE, this)
        NetManager.manager.loadMore(request, id)
    }
}
