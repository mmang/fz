package com.mangmang.fz.adapter.animation

import android.animation.Animator
import android.view.View

/**
 * Created by mangmang on 2017/9/20.
 */
interface BaseAnimation {
    fun getAnimators(view: View): Array<Animator>
}