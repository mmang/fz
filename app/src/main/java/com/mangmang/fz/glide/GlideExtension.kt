package com.mangmang.fz.glide

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.RequestOptions

/**
 * Created by mangmang on 2017/9/28.
 */
@GlideExtension
class GlideExtension {

    private constructor()


    companion object {
        @JvmStatic
        @GlideOption
        fun loadRoundImage(options: RequestOptions, size: Int) {
            options.transform(GlideRoundTransform(size))
        }

        @JvmStatic
        @GlideOption
        fun setImageScaleType(options: RequestOptions, scaleType: ImageView.ScaleType) {

            when (scaleType) {
                ImageView.ScaleType.CENTER_CROP -> {
                    options.centerCrop()
                }
                ImageView.ScaleType.FIT_CENTER -> {
                    options.fitCenter()
                }
                ImageView.ScaleType.CENTER_INSIDE -> options.centerInside()
                else -> options.centerCrop()
            }
        }
    }

}