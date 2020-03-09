package com.example.zhihunews.model


import com.example.zhihunews.service.ZhiHuNewsService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NetManager private constructor() {
    companion object {
        val manager by lazy { NetManager() }
    }

    fun sendRequest(request: ZhiHuNewsRequest) {
        GlobalScope.launch(Dispatchers.Main) {
            ZhiHuNewsService.loadData().awaitAndHandle {
                request.handler.onError(it.message)
            }?.let {
                request.handler.onSuccess(request.type, it)
            }

        }
    }

    fun loadMore(request: ZhiHuNewsRequest, id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            ZhiHuNewsService.loadMoreData(id).awaitAndHandle {
                request.handler.onError(it.message)
            }?.let {
                request.handler.onSuccess(request.type, it)
            }
        }
    }

}

suspend fun <T> Deferred<T>.awaitAndHandle(handler: suspend (Throwable) -> Unit = {}): T? =
    try {
        await()
    } catch (t: Throwable) {
        handler(t)
        null
    }