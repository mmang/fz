package com.mangmang.fz.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by mangmang on 2017/9/19.
 */
class MineFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tv = TextView(context)
        tv.text = "mine fragment"
        return tv
    }
}