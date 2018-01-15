package com.mangmang.fz.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * Created by mangmang on 2017/9/27.
 */
@com.bumptech.glide.annotation.GlideModule
class OkHttpGlideModule : AppGlideModule() {


    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}