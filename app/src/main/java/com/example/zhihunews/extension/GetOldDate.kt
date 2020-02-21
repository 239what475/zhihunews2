package com.example.zhihunews.extension

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.text.ParseException
import java.util.*

//一个计算多少天前日期的方法
fun getOldDate(distanceDay: Int): String {
    val dft = SimpleDateFormat("yyyyMMdd")
    val beginDate = Date()
    val date = Calendar.getInstance().also {
        it.time = beginDate
        it.set(Calendar.DATE, it.get(Calendar.DATE) -distanceDay)
    }

    var endDate: Date? = null

    try {
        endDate = dft.parse(dft.format(date.time))
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return dft.format(endDate)
}