package com.example.zhihunews.extension

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager

internal fun setStatusBar(activity: Activity, dark: Boolean) {

    activity.window.also { it ->
        it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        it.statusBarColor = Color.TRANSPARENT
        it.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        it.decorView.also {
            if (dark) {
                it.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                it.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }


}