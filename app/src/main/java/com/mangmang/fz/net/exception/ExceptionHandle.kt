package com.mangmang.fz.net.exception

import android.net.ParseException
import android.text.TextUtils
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException

/**
 * Created by wangzhenguang on 2018/1/4.
 */
class ExceptionHandle{

    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ResponeThrowable {
        val ex: ResponeThrowable
        return when (e) {
            is HttpException -> {
                ex = ResponeThrowable(e, ERROR.HTTP_ERROR)
                when (e.code()) {
//                    retrofit2.adapter.rxjava2.HttpException: HTTP 504 Unsatisfiable Request (only-if-cached)
                    UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT,
                    INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> {
                        ex.message = "网络错误，请确认网络是否正常"
                    }
                    else -> {
                        ex.message = "网络错误，请确认网络是否正常"
                    }
                }
                return ex
            }
            is ServerException -> {
                ex = ResponeThrowable(e, e.code)
                ex.message = e.message
                return ex
            }
            is JsonParseException, is JSONException
                , is ParseException -> {
                ex = ResponeThrowable(e, ERROR.PARSE_ERROR)
                ex.message = "JSON解析错误"
                return ex
            }
            is ConnectException -> {
                ex = ResponeThrowable(e, ERROR.NETWORD_ERROR)
                ex.message = "连接失败，稍后再试"
                return ex
            }
            is javax.net.ssl.SSLHandshakeException -> {
                ex = ResponeThrowable(e, ERROR.SSL_ERROR)
                ex.message = "证书验证失败"
                return ex
            }
            is ConnectTimeoutException -> {
                ex = ResponeThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.message = "连接超时，稍后再试"
                return ex
            }

            is java.net.SocketTimeoutException -> {
                ex = ResponeThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.message = "连接超时，稍后再试"
                return ex
            }
            else -> {
                ex = ResponeThrowable(e, ERROR.UNKNOWN)
                ex.message = if (TextUtils.isEmpty(e.message)) "未知错误" else e.message
                return ex
            }
        }

    }


    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 未知错误
         */
        val UNKNOWN = 1000
        /**
         * 解析错误
         */
        val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        val NETWORD_ERROR = 1002
        /**
         * 协议出错
         */
        val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = 1006
        /***
         * 登录超时或过期
         */
        const val LOGIN_TIMEOUT = "0001"
        /**
         * 多设备登录
         */
        const val LOGIN_MULIT_DEVICE = "0007"
    }

    class ResponeThrowable(throwable: Throwable?, var code: Int) : Exception(throwable) {
        override var message: String? = null
    }

    class ServerException(status: String, msg: String) : RuntimeException() {
        var code: Int = 0
    }

}