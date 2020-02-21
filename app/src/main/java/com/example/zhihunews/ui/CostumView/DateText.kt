package com.example.zhihunews.ui.CostumView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.zhihunews.R
import com.example.zhihunews.extension.getOldDate
import kotlinx.android.synthetic.main.datetextlayout.view.*
import org.w3c.dom.Text

class DateText:RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private var dateText :TextView
    init {
        val view = View.inflate(context,R.layout.datetextlayout,this)
        dateText = view.findViewById(R.id.datetext)
    }
    fun setData(date: String) {
        dateText.text = date
    }
}
