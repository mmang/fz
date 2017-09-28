package com.mangmang.fz.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mangmang.fz.R
import com.mangmang.fz.glide.GlideApp
import com.mangmang.fz.glide.GlideRoundTransform
import retrofit2.http.Url
import java.io.File

/**
 * Created by mangmang on 2017/9/26.
 */
object GlideUtil {

    val BLUR_VALUE = 20 //模糊
    val CORNER_RADIUS = 20//圆角
    val THUMB_SIZE = 0.5f // 0-1之间 10%原图大小

    val options = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher_round)
            .fallback(R.mipmap.welcome_bg)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)


    fun loadImage(context: Context?, imagetView: ImageView, imageUrl: String?) {
        loadImage(context, imagetView, imageUrl, true)
    }

    fun loadFile(context: Context, imageView: ImageView, file: File) {
        Glide.with(context)
                .load(file)
                .apply(options)
                .transition(DrawableTransitionOptions().crossFade(2000))
                .into(imageView)
    }


    fun loadThumbnailImage(context: Context, imageView: ImageView, url: String) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    private fun loadImage(context: Context?, imageView: ImageView, url: String?, isFade: Boolean) {
        if (isFade) {
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView)
        } else {

            Glide.with(context)
                    .load(url)
                    .into(imageView)

        }

        GlideApp.with(context)
                .load(url)
                .apply(options)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .into(imageView)


    }


    fun loadImage(context: Context?, imageView: ImageView, url: String, scaleType: ImageView.ScaleType) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(RequestOptions().centerCrop())
                .into(imageView)
    }


    fun loadRoundImage(context: Context?, imageView: ImageView, url: String, scaleType: ImageView.ScaleType) {
        GlideApp
                .with(context)
                .load(url)
                .apply(options)
                .setImageScaleType(scaleType)
                .transition(GenericTransitionOptions())
                .loadRoundImage(4)
                .into(imageView)
    }

    fun loadRoundImage(context: Context?, imageView: ImageView, url: String) {
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

}