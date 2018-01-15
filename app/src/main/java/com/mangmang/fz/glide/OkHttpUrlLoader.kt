package com.mangmang.fz.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.OkHttpClient
import com.bumptech.glide.load.model.ModelLoaderFactory
import okhttp3.Call
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/27.
 */
class OkHttpUrlLoader : ModelLoader<GlideUrl, InputStream> {

    @Inject
    lateinit var client: Call.Factory


    override fun handles(url: GlideUrl): Boolean {
        return true
    }

    override fun buildLoadData(model: GlideUrl, width: Int, height: Int,
                               options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(model, OkHttpStreamFetcher(model))
    }


    class Factory : ModelLoaderFactory<GlideUrl, InputStream> {
        override fun teardown() {
        }

        override fun build(multiFactory: MultiModelLoaderFactory?): ModelLoader<GlideUrl, InputStream> {
            return OkHttpUrlLoader()
        }


    }

}