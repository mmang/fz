package com.mangmang.fz.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import java.io.IOException
import java.io.InputStream
import com.bumptech.glide.load.model.GlideUrl
import android.support.annotation.NonNull
import com.bumptech.glide.util.ContentLengthInputStream
import android.os.Build
import android.util.Log
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.util.Synthetic
import com.mangmang.fz.FZApplication
import okhttp3.*
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/27.
 *
 * https://github.com/bumptech/glide/blob/master/integration/okhttp3/src/OkHttpStreamFetcher
 */
class OkHttpStreamFetcher : DataFetcher<InputStream>, okhttp3.Callback {

    private val TAG = "OkHttpFetcher"

    @Inject
    lateinit var client: OkHttpClient

    private val url: GlideUrl
    @Synthetic
    var stream: InputStream? = null
    @Synthetic
    var responseBody: ResponseBody? = null
    @Volatile var call: Call? = null

    private var callback: DataFetcher.DataCallback<in InputStream>? = null

    constructor(url: GlideUrl) {
        this.url = url
        FZApplication.getsInstance().getAppComponent()
                .inject(this)
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(url.toStringUrl())
        for ((key, value) in url.headers) {
            requestBuilder.addHeader(key, value)
        }
        val request = requestBuilder.build()
        this.callback = callback

        call = client.newCall(request)
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            call?.enqueue(this)
        } else {
            try {
                // Calling execute instead of enqueue is a workaround for #2355, where okhttp throws a
                // ClassCastException on O.
                onResponse(call!!, call!!.execute())
            } catch (e: IOException) {
                onFailure(call!!, e)
            } catch (e: ClassCastException) {
                // It's not clear that this catch is necessary, the error may only occur even on O if
                // enqueue is used.
                onFailure(call!!, IOException("Workaround for framework bug on O", e))
            }

        }
    }

    override fun onFailure(call: Call, e: IOException) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "OkHttp failed to obtain result", e)
        }

        callback!!.onLoadFailed(e)
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body()
        if (response.isSuccessful) {
            val contentLength = responseBody!!.contentLength()
            stream = ContentLengthInputStream.obtain(responseBody!!.byteStream(), contentLength)
            callback!!.onDataReady(stream)
        } else {
            callback!!.onLoadFailed(HttpException(response.message(), response.code()))
        }
    }

    override fun cleanup() {
        try {
            if (stream != null) {
                stream!!.close()
            }
        } catch (e: IOException) {
            // Ignored
        }

        if (responseBody != null) {
            responseBody!!.close()
        }
        callback = null
    }

    override fun cancel() {
        call?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

}