package com.mangmang.fz

import android.content.Context
import android.content.res.Resources

/**
 * Created by lvruheng on 2017/7/4.
 */
object UiUtils {

    fun dip2px(dipValue: Float): Float {
        val scale: Float = Resources.getSystem().displayMetrics.density
        return dipValue * scale + 0.5f
    }

    fun px2dip(pxValue: Float): Int {
        return (pxValue / Resources.getSystem().displayMetrics.density).toInt()
    }
}