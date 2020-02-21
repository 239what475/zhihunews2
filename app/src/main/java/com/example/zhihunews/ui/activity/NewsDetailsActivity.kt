package com.example.zhihunews.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.zhihunews.R
import com.example.zhihunews.adapter.NewsDetailAdapter
import com.example.zhihunews.model.NewsDetailHolder

class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var detailPager :ViewPager
    private lateinit var backButton:Button
    private var current = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        detailPager = findViewById(R.id.webview)
        backButton = findViewById<Button>(R.id.back_btn).also {
            it.setOnClickListener{
                onBackPressed()
            }
        }

        val urlList = NewsDetailHolder.getUrl()
        println(urlList.size)
        current = NewsDetailHolder.getCurrent()
        println(NewsDetailHolder.getCurrent())
        println(current)
        val detailPagerAdapter = NewsDetailAdapter(this,detailPager,urlList)
        detailPager.adapter = detailPagerAdapter
        detailPager.setCurrentItem(current)
    }

}