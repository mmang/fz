package com.mangmang.fz.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import com.mangmang.fz.R

/**
 * Created by wangzhenguang on 2017/12/22.
 */
class ShadowConstraintLayout : ConstraintLayout {

    private val MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto"
    private val ANDROIDXML = "http://schemas.android.com/apk/res/android"


    private val disabledBackgroundColor = Color.parseColor("#E2E2E2")
    private var beforeBackground: Int = 0
    var shapeBackground = Color.parseColor("#FFFFFF")


    private var animation = false


    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null)
            obtainStyledAttrs(context, attrs, defStyleAttr)
    }

    private fun obtainStyledAttrs(context: Context?, attrs: AttributeSet, defStyleAttr: Int) {

        setBackgroundResource(R.drawable.background_button_rectangle)
        val bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1)
        if (bacgroundColor != -1) {
            setShapeBackgroundColor(resources.getColor(bacgroundColor))
        } else {
            // Color by hexadecimal
            val background = attrs.getAttributeValue(ANDROIDXML, "background")
            if (background != null)
                setShapeBackgroundColor(Color.parseColor(background))
            else
                setShapeBackgroundColor(this.shapeBackground)
        }

    }

    //设置shape里的背景颜色
    private fun setShapeBackgroundColor(color: Int) {
        this.shapeBackground = color
        if (isEnabled)
            beforeBackground = shapeBackground
        val layer = background as LayerDrawable
        val shape = layer.findDrawableByLayerId(R.id.shape_bacground) as GradientDrawable
        shape.setColor(shapeBackground)
    }


    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled)
            setBackgroundColor(beforeBackground)
        else
            setBackgroundColor(disabledBackgroundColor)
        invalidate()
    }


    override fun onAnimationStart() {
        super.onAnimationStart()
        animation = true
    }

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        animation = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (animation)
            invalidate()
    }
}