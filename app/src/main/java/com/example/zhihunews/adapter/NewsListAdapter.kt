package com.example.zhihunews.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.zhihunews.service.Story
import com.example.zhihunews.service.TopStory
import com.example.zhihunews.service.ZhiHuNewsBean
import com.example.zhihunews.ui.CostumView.DateText
import com.example.zhihunews.ui.CostumView.LoadMore
import com.example.zhihunews.ui.CostumView.NewsBanner
import com.example.zhihunews.ui.CostumView.NewsCard

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListHolder>() {
    companion object {
        const val ITEM_TYPE_IMAGE = 0
        const val ITEM_TYPE_DATETEXT = 1
        const val ITEM_TYPE_CARD = 2
//        const val PROGRESS_BAR = 3
    }

    private var listener: ((urlList: MutableList<String>, current: Int) -> Unit)? = null
    private val newsPictures by lazy { mutableListOf<TopStory>() }
    private val newsCards by lazy { mutableListOf<Story>() }
    private val newsDate by lazy { mutableListOf<String>() }
    val newsUrl by lazy { mutableListOf<String>() }
    val emptyCard = Story("", "", 0, "", listOf(""), "", 0, "")
    var totalDays = 0
    var todayNewsSize = 0
    var yesDayNewsSize = 0

    fun setCardOnClickListener(listener: (urlList: MutableList<String>, current: Int) -> Unit) {
        this.listener = listener
    }

    fun loadData(newsData: ZhiHuNewsBean, counter: Int) {
        if (counter == 1) {
            totalDays = 1
            todayNewsSize = newsData.stories.size
            this.newsCards.clear()
            this.newsPictures.clear()
            this.newsUrl.clear()
            this.newsPictures.addAll(newsData.top_stories)
        }
        this.newsCards.add(emptyCard)
        this.newsCards.addAll(newsData.stories)
        this.newsDate.let {
            it.add(newsData.date)
            for (news in newsData.stories) {
                it.add("")
                newsUrl.add(news.url)
            }
        }
        if (counter == 2) {
            totalDays++
            yesDayNewsSize = newsData.stories.size
            notifyDataSetChanged()
        }
    }

    fun loadMoreData(newsData: ZhiHuNewsBean, counter: Int) {
        totalDays++
        println(totalDays)
        this.newsCards.add(emptyCard)
        this.newsCards.addAll(newsData.stories)
        this.newsDate.let {
            it.add(newsData.date)
            for (news in newsData.stories) {
                it.add("")
                newsUrl.add(news.url)
            }
        }
        if (counter % 5 == 0) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        return when (viewType) {
            ITEM_TYPE_IMAGE -> NewsListHolder(NewsBanner(parent.context))
            ITEM_TYPE_DATETEXT -> NewsListHolder(DateText(parent.context))
            else -> NewsListHolder(NewsCard(parent.context))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TYPE_IMAGE
        if (newsCards.size == 0) {
            return ITEM_TYPE_CARD
        } else {
            if (newsCards[position].id == 0) {
                println("$position + date")
                return ITEM_TYPE_DATETEXT
            } else {
                println("$position + card")
                return ITEM_TYPE_CARD
            }
        }
    }

    override fun getItemCount(): Int {
        return when (newsCards.size) {
            0 -> 10
            else -> newsCards.size
        }
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        when (position) {
            0 -> {
                if (newsPictures.size == 0) return
                val itemView = holder.itemView as NewsBanner
                itemView.setData(newsPictures)
                itemView.refreshDots()
            }
//            itemCount -> return
            else -> {
                if (newsPictures.size == 0) return
                if (newsCards[position].id == 0) {
                    println("$position + date")
                    val itemView = holder.itemView as DateText
                    val date = newsDate[position]
                    itemView.setData(date)
                } else {
                    println("$position + card")
                    val itemView = holder.itemView as NewsCard
                    itemView.setData(newsCards, position)
                    itemView.setOnClickListener {
                        if (position <= todayNewsSize)
                            listener?.invoke(newsUrl, position - 1)
                        if (position > todayNewsSize)
                            listener?.invoke(
                                newsUrl,
                                position - (position - todayNewsSize - 1) / (yesDayNewsSize + 1) - 2
                            )

                    }
                }
            }
        }
    }

    class NewsListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}