package com.mangmang.fz.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.LibraryGlideModule
import java.io.InputStream
import com.bumptech.glide.GlideBuilder



/**
 * Created by mangmang on 2017/9/27.
 */
@GlideModule
class OkHttpLibraryGlideModule : LibraryGlideModule() {

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }
}