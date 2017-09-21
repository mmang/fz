package com.mangmang.fz.base

import io.reactivex.disposables.Disposable

/**
 * Created by mangmang on 2017/9/20.
 */
interface ISubscriber<T : BaseResponse> {

    fun doOnSubscribe(d: Disposable)
    fun doOnError(errorMessage: String)
    fun doOnNext(t: T)
    fun doOnCompleted()
}