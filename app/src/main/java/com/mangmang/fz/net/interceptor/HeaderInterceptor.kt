package com.wzg.readbook.net.interceptor

import com.mangmang.fz.bean.User
import com.mangmang.fz.utils.UserManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by mangmang on 2017/8/28.
 */
class HeaderInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val build = original.newBuilder()
                .addHeader("token", UserManager.getToken())
                .build()

        return chain.proceed(build)
    }
}