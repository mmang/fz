package com.mangmang.fz.glide

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest


/**
 * Created by mangmang on 2017/9/27.
 */
class GlideRoundTransform(dp: Int = 4) : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {
    }


    init {
        radius = Resources.getSystem().displayMetrics.density * dp.toFloat()
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return roundCrop(pool, toTransform)
    }

    companion object {
        var radius: Float = 0f

        private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null
            val storkeWidth = Resources.getSystem().displayMetrics.density * 1

            var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result)
            val paint = Paint()
            paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            var rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, paint)



            val storePaint = Paint()

            storePaint.color = Color.BLACK
            storePaint.isDither = true
            storePaint.strokeWidth = storkeWidth
            storePaint.style = Paint.Style.STROKE
            storePaint.isAntiAlias = true

            rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
            canvas.drawRoundRect(rectF, radius, radius, storePaint)


            return result
        }
    }
}