package com.example.zhihunews.ui.CostumView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.example.zhihunews.R
import com.example.zhihunews.service.Story

class NewsCard :RelativeLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private  var newsTitle :TextView
    private  var newsSubTitle:TextView
    private  var newsPicture: ImageView
    private  var newsCard:CardView

    init {
        val view = View.inflate(context, R.layout.itemlayout,this)
        newsCard = view.findViewById(R.id.newsCard)
        newsPicture = view.findViewById(R.id.newsPicture)
        newsTitle = view.findViewById(R.id.newsTitle)
        newsSubTitle = view.findViewById(R.id.summarize)
    }

    fun setData(
        newsCards: MutableList<Story>,
        position: Int
    ) {
        if (position< newsCards.size){
            newsTitle.text = newsCards[position].title
            newsSubTitle.text = newsCards[position].hint
            Glide.with(context).load(newsCards[position].images[0]).asBitmap().into(newsPicture)
        }
    }

}