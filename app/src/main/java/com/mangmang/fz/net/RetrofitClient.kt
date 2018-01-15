package com.mangmang.fz.net

import android.content.Context
import android.util.Log
import com.wzg.readbook.net.interceptor.HeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * Created by mangmang on 2017/9/13.
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {

    var httpCacheDirectory: File
    val mContext: Context = context
    var cache: Cache? = null
    var okHttpClient: OkHttpClient
    var retrofit: Retrofit
    var DEFAULT_TIMEOUT: Long = 30


    init {
        httpCacheDirectory = File(mContext.cacheDir, "app_cache")
        try {
            cache = Cache(httpCacheDirectory, 10 * 1024 * 1034)
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }

        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(CacheInterceptor(context))
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build()


        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()


    }

    companion object {
        @Volatile
       private var instance: RetrofitClient? = null
        fun getInstance(context: Context): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    instance = instance ?: RetrofitClient(context, ApiService.BASE_URL)
                }
            }
            return instance!!
        }
    }

    fun <T> create(service: Class<T>?): T?{
        if (service == null) {
            throw RuntimeException("Api Service is null")
        }
        return retrofit?.create(service)
    }


}

