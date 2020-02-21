package com.example.zhihunews.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.zhihunews.R
import com.example.zhihunews.adapter.NewsListAdapter
import com.example.zhihunews.extension.getOldDate
import com.example.zhihunews.extension.setStatusBar
import com.example.zhihunews.model.NewsDetailHolder
import com.example.zhihunews.presenter.ZhiHuNewsPresenterImpl
import com.example.zhihunews.service.ZhiHuNewsBean
import com.example.zhihunews.view.ZhiHuNewsView

class ZhiHuNewsActivity :AppCompatActivity(), ZhiHuNewsView {
    override fun loadMoreSuccess(list: ZhiHuNewsBean,counter:Int) {
//        myToasty(list.toString())
        adapter.loadMoreData(list,counter)
        if (counter%5 != 0){
            presenter.loadMore(getOldDate(adapter.totalDays-1))
        }
    }

    override fun onError(message: String?) {
        myToasty("失败")
        refreshLayout.isRefreshing = false
    }

    override fun loadSuccess(list: ZhiHuNewsBean,counter:Int) {
//        myToasty(list.toString())
        adapter.loadData(list,counter)
        if (counter == 1){
            presenter.loadMore(getOldDate(adapter.totalDays-1))
        }else{
            refreshLayout.isRefreshing = false
        }
    }

    private fun myToasty(message:CharSequence){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).also {
            it.setGravity(Gravity.BOTTOM,0,0)
            it.show()
        }
    }

    private lateinit var inflater: LayoutInflater
    private lateinit var toolbar: Toolbar
    private lateinit var daytextView: TextView
    private lateinit var monthtextview: TextView
    private lateinit var textView: TextView
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var  manager:LinearLayoutManager
    private var currentPosition = 0

    private val adapter by lazy { NewsListAdapter() }
    private val presenter by lazy { ZhiHuNewsPresenterImpl(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        inflater = LayoutInflater.from(this)

        setStatusBar(this,true)

        replaceActionBar()

        refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefreshlayout).also {
            it.setColorSchemeColors(Color.BLUE)
            it.setOnRefreshListener {
                presenter.daysCounter = 0
                presenter.loadData()
            }
        }

        myRecyclerView = findViewById<RecyclerView>(R.id.container).also {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            it.adapter = adapter
            it.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        manager = recyclerView.layoutManager as LinearLayoutManager
                        val lastPosition = manager.findLastVisibleItemPosition()
                        if (lastPosition == adapter.itemCount-1)
                            presenter.loadMore(getOldDate(adapter.totalDays-1))
                    }
                }
            })
        }

        adapter.setCardOnClickListener {urlList,current->
            println(urlList.size)
            val intent = Intent(this,NewsDetailsActivity::class.java)
            NewsDetailHolder.setCurrent(current)
            NewsDetailHolder.setUrl(urlList)
            startActivity(intent)
        }
        initData()
    }

    private fun initData() {
        presenter.loadData()
    }

    @SuppressLint("SetTextI18n")
    private fun replaceActionBar() {
        val calendar = Calendar.getInstance().also {
            it.timeZone = TimeZone.getTimeZone("GMT+8:00")
        }

        toolbar = findViewById<Toolbar>(R.id.toolbar).also {
            setSupportActionBar(it)
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.toolbarBackground))
            it.setTitleTextAppearance(this, R.style.Toolbar_TitleText)
            it.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitleText)
        }

        textView = findViewById<TextView>(R.id.text1).also{
            it.text = "知乎日报"
            it.textSize = 40f
        }

        daytextView = findViewById<TextView>(R.id.daytext).also{
            it.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
        }

        monthtextview = findViewById<TextView>(R.id.monthtext).also {
            it.text = "${(calendar.get(Calendar.MONTH) + 1)} 月"
        }

    }

//    override fun onPause() {
//        super.onPause()
//        currentPosition = manager.findFirstVisibleItemPosition()
//        println("$currentPosition +=============================")
//    }

//    override fun onResume() {
//        super.onResume()
//        val position = currentPosition+NewsDetailHolder.getCurrent()+NewsDetailHolder.getCurrent()/7
//        myRecyclerView.scrollToPosition(position)

//        val firstItem = manager.findFirstVisibleItemPosition()
//        val lastItem = manager.findLastVisibleItemPosition()
//        val position = NewsDetailHolder.getCurrent()
//        println(position)
//        if (position <=firstItem){
//            myRecyclerView.scrollToPosition(position)
//        }else if (position <= lastItem){
//            val top = myRecyclerView.getChildAt(position-firstItem).top
//            myRecyclerView.scrollBy(0,top)
//        }else{
//            myRecyclerView.scrollToPosition(position)
//        }
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            val intent = Intent(Intent.ACTION_MAIN)//ACTION_MAIN：应用程序入口点
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK//FLAG_ACTIVITY_NEW_TASK：默认的跳转类型,会重新创建一个新的Activity
            intent.addCategory(Intent.CATEGORY_HOME)//CATEGORY_HOME：显示当前应用的主界面
            applicationContext.startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}


