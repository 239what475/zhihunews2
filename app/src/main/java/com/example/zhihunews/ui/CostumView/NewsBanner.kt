package com.example.zhihunews.ui.CostumView

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.zhihunews.R
import com.example.zhihunews.ThreadUtil
import com.example.zhihunews.adapter.NewsBannerAdapter
import com.example.zhihunews.model.*
import com.example.zhihunews.service.TopStory
import com.example.zhihunews.service.ZhiHuNewsBean
import kotlinx.android.synthetic.main.itemlayout.view.*
import java.util.ArrayList

class NewsBanner : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var pictureBanner: ViewPager
    private var mDots: List<View>
    private var linearLayoutDots: LinearLayout
    private val dots = IntArray(6)

    init {
        val view = View.inflate(context, R.layout.imagepagerlayout, this)
        pictureBanner = view.findViewById(R.id.imagepager)
        val params = pictureBanner.getLayoutParams()
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = context.resources.displayMetrics.widthPixels
        pictureBanner.requestLayout()
        linearLayoutDots = view.findViewById(R.id.dotslayout)
        mDots = addDots(linearLayoutDots, 5, view)
    }

    fun setData(list: MutableList<TopStory>) {
        val adapter = NewsBannerAdapter(
            context,
            pictureBanner,
            getBannerPictures(list),
            getBannerPicturesTitle(list),
            getBannerPicturesHint(list),
            getBannerPicturesUrl(list),
            getBannerPicturesFuzzyColor(list)
        )
        pictureBanner.let {
            it.adapter = adapter
            it.currentItem = 10
        }

        linearLayoutDots.getChildAt(0).findViewById<ImageView>(dots[0])
            .setImageResource(R.drawable.dot_selected)
        val firstDotParams =
            mDots[0].getLayoutParams() as LinearLayout.LayoutParams
        firstDotParams.width = 40
        firstDotParams.height = 40
        firstDotParams.setMargins(20, 15, 20, 15)

        pictureBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                val dotposition = position % 5
                linearLayoutDots.getChildAt(dotposition).findViewById<ImageView>(dots[dotposition])
                    .setImageResource(R.drawable.dot_selected)

                val newDotParams =
                    mDots[dotposition].getLayoutParams() as LinearLayout.LayoutParams
                newDotParams.width = 40
                newDotParams.height = 40
                newDotParams.setMargins(20, 15, 20, 15)

                val lastposition = (position + 4) % 5
                linearLayoutDots.getChildAt(lastposition)
                    .findViewById<ImageView>(dots[lastposition])
                    .setImageResource(R.drawable.dot_normal)

                val oldDotParams =
                    mDots[lastposition].getLayoutParams() as LinearLayout.LayoutParams
                oldDotParams.let {
                    it.setMargins(20, 20, 20, 20)
                    it.width = 30
                    it.height = 30
                }

                val nextposition = (position + 6) % 5
                linearLayoutDots.getChildAt(nextposition)
                    .findViewById<ImageView>(dots[nextposition])
                    .setImageResource(R.drawable.dot_normal)

                val nextDotParams =
                    mDots.get(nextposition).getLayoutParams() as LinearLayout.LayoutParams
                nextDotParams.setMargins(20, 20, 20, 20)
                nextDotParams.width = 30
                nextDotParams.height = 30

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })


    }


    private fun addDot(linearLayout: LinearLayout, id: Int): Int {
        val dotParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).also {
            it.width = 30
            it.height = 30
            it.setMargins(20, 20, 20, 20)
        }

        val dot = ImageView(context).also {
            it.layoutParams = dotParams
            it.setImageResource(R.drawable.dot_normal)
            it.id = View.generateViewId()
            linearLayout.addView(it)
        }

        dots[id] = dot.id
        return dot.id
    }

    private fun addDots(linearLayout: LinearLayout, number: Int, view: View): List<View> {
        val dots = ArrayList<View>()
        for (i in 0 until number) {
            val dotId = addDot(linearLayout, i)
            dots.add(view.findViewById(dotId))
        }
        return dots
    }

     fun refreshDots() {
        val firstdot = mDots[0].layoutParams as LinearLayout.LayoutParams
        firstdot.also {
            it.width = 40
            it.height = 40
        }
        linearLayoutDots.getChildAt(0).findViewById<ImageView>(dots[0])
            .setImageResource(R.drawable.dot_selected)

        for (i in 1 until 5) {
            val nextdots = mDots[i].layoutParams as LinearLayout.LayoutParams
            nextdots.also {
                it.width = 30
                it.height = 30
            }
            linearLayoutDots.getChildAt(i).findViewById<ImageView>(dots[i])
                .setImageResource(R.drawable.dot_normal)


        }
    }


}