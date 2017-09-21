package com.mangmang.fz.adapter.animation

import android.animation.Animator
import android.view.View
import android.animation.ObjectAnimator


/**
 * Created by mangmang on 2017/9/20.
 */
class AlphaInAnimation : BaseAnimation {


    var from: Float

    constructor(from: Float = 0f) {
        this.from = from
    }

    override fun getAnimators(view: View): Array<Animator> {
        return arrayOf(ObjectAnimator.ofFloat(view, "alpha", from, 1f))
    }


}