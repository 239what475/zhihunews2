package com.example.zhihunews.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class NewsDetailAdapter(
    val context: Context,
    val viewPager: ViewPager,
    val urlList: MutableList<String>
):PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return urlList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val webView = WebView(context)
        val webSettings = webView.settings
        // 加载图片
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webView.loadUrl(urlList[position])
        viewPager.addView(webView)
        return webView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}