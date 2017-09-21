package com.mangmang.fz

import com.wzg.readbook.interceptor.LoggingInterceptor

/**
 * Created by mangmang on 2017/8/28.
 */
class Logger : LoggingInterceptor.Logger{
    override fun log(message: String) {
        LogUtils.i("http : $message ")
    }

}