package com.mangmang.fz.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mangmang.fz.R
import com.mangmang.fz.glide.GlideApp
import com.mangmang.fz.glide.GlideRequest
import java.io.File

/**
 * Created by mangmang on 2017/9/26.
 */
object GlideUtil {

    val BLUR_VALUE = 20 //模糊
    val CORNER_RADIUS = 20//圆角
    val THUMB_SIZE = 0.5f // 0-1之间 10%原图大小

    private val options = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
//            .fallback(R.mipmap.welcome_bg)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    private lateinit var newOptions: RequestOptions


    fun loadFile(context: Context, imageView: ImageView, file: File) {
        Glide.with(context)
                .load(file)
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade(2000))
                .into(imageView)
    }


    fun loadImage(context: Context?, url: String?, imageView: ImageView, scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP) {
        GlideApp.with(context)
                .load(url)
                .setImageScaleType(scaleType)
                .into(imageView)

    }


    lateinit var glideRequest: GlideRequest<Drawable>


    fun load(context: Context?, url: String?, thumbnailUrl: String = ""): GlideUtil {
        glideRequest = GlideApp.with(context)
                .load(url)

        if (thumbnailUrl.isNotEmpty())
            glideRequest.apply {
                this.thumbnail(com.bumptech.glide.Glide.with(context).load(thumbnailUrl))
            }
        return this
    }

    fun placeholder(resourceId: Int): GlideUtil {
        options.placeholder(resourceId)
        return this
    }

    fun error(error: Int): GlideUtil {
        options.error(error)
        return this
    }

    fun fallback(resourceId: Int): GlideUtil {
        options.fallback(resourceId)
        return this
    }


    fun into(imageView: ImageView, loadPlaceHoder: Boolean = true, scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP) {

        glideRequest.apply {
            setImageScaleType(scaleType)
            if (loadPlaceHoder) apply(options)
            into(imageView)
        }
    }


    fun loadRoundImage(context: Context?, imageView: ImageView, url: String?, scaleType: ImageView.ScaleType) {
        GlideApp
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(options)
                .setImageScaleType(scaleType)
                .transition(GenericTransitionOptions())
                .loadRoundImage(4)
                .into(imageView)
    }

    fun loadRoundImage(context: Context?, imageView: ImageView, url: String?) {
        loadRoundImage(context, imageView, url, ImageView.ScaleType.CENTER_CROP)
    }

    fun resumeRequests(context: Context) {
        Glide.with(context)
                .resumeRequests()
    }

    fun pasuseRequest(context: Context) {
        Glide.with(context)
                .pauseRequests()
    }

    fun clearDiskCache(context: Context) {
        Thread(Runnable {
            Glide.get(context)
                    .clearDiskCache()
        }).start()
    }

    fun clearMemory(context: Context) {
        Glide.get(context)
                .clearMemory()
    }

    fun loadThumbnailImage(context: Context, imageView: ImageView, url: String) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

}