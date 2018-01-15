package com.mangmang.fz.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.scwang.smartrefresh.layout.util.DensityUtil.dp2px
import android.util.TypedValue
import com.mangmang.fz.widget.RoundImageView
import android.content.res.TypedArray
import android.graphics.*
import com.mangmang.fz.R
import android.os.Build
import android.graphics.drawable.Drawable


/**
 * Created by wangzhenguang on 2017/12/21.
 */
class RoundImageView : ImageView {

    /**
     * 圆形模式
     */
    private val MODE_CIRCLE = 1
    /**
     * 普通模式
     */
    private val MODE_NONE = 0
    /**
     * 圆角模式
     */
    private val MODE_ROUND = 2
    /**
     *单个圆角自定模式
     */
    private val MODE_CUS_ROUND = 3

    private lateinit var mPaint: Paint
    private var currMode = MODE_ROUND

    /**
     * 圆角半径
     */
    private var currRound = dp2px(4f)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null)
            obtainStyledAttrs(context, attrs, defStyleAttr)
        initViews()
    }


    private fun obtainStyledAttrs(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0)
        currMode = if (a.hasValue(R.styleable.RoundImageView_type)) a.getInt(R.styleable.RoundImageView_type, MODE_NONE) else MODE_NONE
        currRound = if (a.hasValue(R.styleable.RoundImageView_radius)) a.getDimensionPixelSize(R.styleable.RoundImageView_radius, currRound) else currRound



        a.recycle()
    }

    private fun initViews() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    }

    private fun dp2px(value: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics).toInt()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /**
         * 当模式为圆形模式的时候，我们强制让宽高一致
         */
        if (currMode === MODE_CIRCLE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            val result = Math.min(measuredHeight, measuredWidth)
            setMeasuredDimension(result, result)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val mDrawable = drawable
        val mDrawMatrix = imageMatrix
        if (mDrawable == null) {
            return  // couldn't resolve the URI
        }

        if (mDrawable.intrinsicWidth == 0 || mDrawable.intrinsicHeight == 0) {
            return      // nothing to draw (empty bounds)
        }

        if (mDrawMatrix == null && paddingTop == 0 && paddingLeft == 0) {
            mDrawable.draw(canvas)
        } else {
            val saveCount = canvas.getSaveCount()
            canvas.save()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (cropToPadding) {
                    val scrollX = scrollX
                    val scrollY = scrollY
                    canvas.clipRect(scrollX + paddingLeft, scrollY + paddingTop,
                            scrollX + right - left - paddingRight,
                            scrollY + bottom - top - paddingBottom)
                }
            }
            canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
            if (currMode === MODE_CIRCLE) {//当为圆形模式的时候
                val bitmap = drawable2Bitmap(mDrawable)
                mPaint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
                canvas.drawCircle(width.toFloat() / 2, height.toFloat() / 2, width.toFloat() / 2, mPaint)
            } else if (currMode === MODE_ROUND) {//当为圆角模式的时候
                val bitmap = drawable2Bitmap(mDrawable)
                mPaint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
                canvas.drawRoundRect(RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat()),
                        currRound.toFloat(), currRound.toFloat(), mPaint)
            } else if (currMode == MODE_CUS_ROUND) {
                val bitmap = drawable2Bitmap(mDrawable)
                mPaint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
                val roundRectF = RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat())
                val path = Path()
                path.addRoundRect(roundRectF, floatArrayOf(currRound.toFloat(), currRound.toFloat(), currRound.toFloat(), currRound.toFloat(), 0f, 0f, 0f, 0f), Path.Direction.CW)
                canvas.drawPath(path, mPaint)
            } else {
                if (mDrawMatrix != null) {
                    canvas.concat(mDrawMatrix)
                }
                mDrawable.draw(canvas)
            }
            canvas.restoreToCount(saveCount)
        }
    }

    /**
     * drawable转换成bitmap
     */
    private fun drawable2Bitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        //根据传递的scaletype获取matrix对象，设置给bitmap
        val matrix = imageMatrix
        if (matrix != null) {
            canvas.concat(matrix)
        }
        drawable.draw(canvas)
        return bitmap
    }

}