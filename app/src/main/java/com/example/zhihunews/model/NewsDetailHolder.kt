package com.example.zhihunews.model

object NewsDetailHolder {
    private val detailUrl by lazy { mutableListOf<String>() }
    private var currentItem = 0

    fun setUrl(urlList: MutableList<String>){
        this.detailUrl.clear()
        this.detailUrl.addAll(urlList)
    }
    fun setCurrent(current:Int){
        this.currentItem = current
    }

    fun getUrl() = this.detailUrl
    fun getCurrent() = this.currentItem
}