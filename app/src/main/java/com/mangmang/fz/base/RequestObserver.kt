package com.mangmang.fz.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by mangmang on 2017/9/20.
 */
abstract class RequestObserver<T : BaseResponse> : Observer<T>, ISubscriber<T> {


    override fun onNext(value: T) {
        doOnNext(value)
    }

    override fun onError(e: Throwable) {

        val errorMsg = when (e) {
            is SocketTimeoutException -> {
                "网络连接超时"
            }
            is ConnectException -> {
                "网络连接失败"
            }
            is UnknownHostException -> {
                "未知域名"
            }
            else -> {
                e.message
            }
        }

        if (errorMsg != null) {
            doOnError(errorMsg)
        }
    }


    override fun onComplete() {
        doOnCompleted()
    }

    override fun onSubscribe(d: Disposable) {
        doOnSubscribe(d)
    }


}