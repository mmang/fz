package com.mangmang.fz.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.mangmang.fz.R

/**
 * Created by mangmang on 2017/9/18.
 */
class LoadingDialog : Dialog {

    constructor(context: Context) : super(context, R.style.base_dialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_progress)
        setCanceledOnTouchOutside(false)
        var attrs = window.attributes

        attrs.width = context.resources.getDimension(R.dimen.x300).toInt()
        attrs.height = context.resources.getDimension(R.dimen.y300).toInt()
        attrs.gravity = Gravity.CENTER
        window.attributes = attrs
    }
}