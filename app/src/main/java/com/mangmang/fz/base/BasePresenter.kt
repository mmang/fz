package com.mangmang.fz.base

import android.content.Context
import com.mangmang.fz.BaseContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.mangmang.fz.net.ApiService
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/13.
 */
abstract class BasePresenter<T : BaseContract.BaseV> : BaseContract.BaseP {


    @Inject
    lateinit var apiService: ApiService


    var view: T? = null

    /**
     * 没有找到办法来用限定
     */
    override fun attachView(view: Any) {
        if (view !is BaseContract.BaseV) {
            throw Exception("view 必须继承BaseContract.BaseV")
        }
        this.view = view as T
        loadData()
    }

    override fun loadData() {
    }

    override fun detachView() {
        this.view = null
    }


    protected fun getActivityLifecycleProvider(): LifecycleProvider<Any>? {
        var provider: LifecycleProvider<Any>? = null
        if (null != this.view && this.view is LifecycleProvider<*>) {
            provider = this.view as LifecycleProvider<Any>
        }
        return provider
    }

    fun subscribe(observable: Observable<Any>, observer: Observer<Any>): Observable<Any> {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getActivityLifecycleProvider()?.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(observer)
        return observable
    }


    val schedulersTransformer: ObservableTransformer<*, *> = object : ObservableTransformer<Any, Any> {
        override fun apply(upstream: Observable<Any>?): ObservableSource<Any> {
            return upstream!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(getActivityLifecycleProvider()?.bindUntilEvent(ActivityEvent.DESTROY))
        }


    }

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return schedulersTransformer as ObservableTransformer<T, T>
    }


}