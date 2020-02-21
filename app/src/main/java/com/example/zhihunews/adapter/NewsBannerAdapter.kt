package com.example.zhihunews.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.zhihunews.R
import com.example.zhihunews.model.NewsDetailHolder
import com.example.zhihunews.ui.activity.NewsDetailsActivity
import com.example.zhihunews.ui.activity.ZhiHuNewsActivity
import kotlinx.android.synthetic.main.activity_web.view.*

class NewsBannerAdapter(
    val context: Context,
    val viewPager: ViewPager,
    val picture:List<String>,
    val title:List<String>,
    val subTitle:List<String>,
    val webUrl:List<String>,
    val subPicture:List<String>
) : PagerAdapter() {
    private lateinit var newsPicture: ImageView
    private lateinit var picturePlus: ImageView
    private lateinit var pictureTitle: TextView
    private lateinit var pictureSubTitle: TextView
    private val url by lazy { mutableListOf<String>() }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        url.addAll(webUrl)
        val newPosition = position%title.size
        val color = subPicture.get(newPosition).replace("0x","#")
        val colors = intArrayOf(
            Color.parseColor("#00FFFFFF"),
            Color.parseColor("#00FFFFFF"),
            Color.parseColor("#00FFFFFF"),
            Color.parseColor(color),
            Color.parseColor(color))
        val view = LayoutInflater.from(container.context).inflate(R.layout.webview,container,false)
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,colors)
        gradientDrawable.cornerRadius = 0f

        newsPicture = view.findViewById<ImageView>(R.id.webImage).also {
            Glide.with(context).load(picture.get(newPosition)).asBitmap().dontAnimate().into(it)
        }

        picturePlus = view.findViewById<ImageView>(R.id.webBackground).also {
            it.background = gradientDrawable
            val params = it.layoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = params.width
            it.setOnClickListener {
                val intent = Intent(context,NewsDetailsActivity::class.java)
                NewsDetailHolder.setUrl(url)
                NewsDetailHolder.setCurrent(newPosition)
                context.startActivity(intent)
            }
        }


        pictureTitle = view.findViewById<TextView>(R.id.webTitle).also {
            it.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            it.text = title.get(newPosition)
        }
        pictureSubTitle = view.findViewById<TextView>(R.id.webSubTitle).also {
            it.text = subTitle.get(newPosition)
        }

        viewPager.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}