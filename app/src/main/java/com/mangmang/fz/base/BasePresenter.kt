package com.happyfi.lelerong.base

import android.app.Activity
import android.app.Fragment
import com.mangmang.fz.net.ApiService
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/13.
 */
abstract class BasePresenter<T : BaseContract.BaseV> : BaseContract.BaseP {


    @Inject
    lateinit var apiService: ApiService


    var view: T? = null

    /**
     * 没有找到办法来解决这个强转
     *
     * 如果在baseP上面用泛型就无法再基类里统一 attach
     * 需要在每个 baseV 的子类去手动设置
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

    inline fun <reified T> isA(value: Any) = value is T

    /**
     * 获取rxlifecyle
     */
    protected fun getActivityLifecycleProvider(): LifecycleProvider<Any>? {
        var provider: LifecycleProvider<Any>? = null
        if (null != this.view && this.view is LifecycleProvider<*>) {
            provider = this.view as LifecycleProvider<Any>
        }
        return provider
    }

    fun <I> subscribe(observable: Observable<I>, observer: Observer<I>): Observable<I> {
        //判断view是activity or  fragment
        val activityLifecycleProvider = getActivityLifecycleProvider()

        observable.apply {
            this.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            if (activityLifecycleProvider is Activity) {
                this.compose(getActivityLifecycleProvider()?.bindUntilEvent(ActivityEvent.DESTROY))
            } else if (activityLifecycleProvider is Fragment) {
                this.compose(activityLifecycleProvider?.bindUntilEvent(FragmentEvent.DETACH))
            }
            subscribe(observer)
        }
        return observable
    }

    val schedulersTransformer: ObservableTransformer<*, *> = object : ObservableTransformer<Any, Any> {
        override fun apply(upstream: Observable<Any>?): ObservableSource<Any> {
            return upstream!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(getActivityLifecycleProvider()?.bindUntilEvent(ActivityEvent.DESTROY))

        }
    }

    open fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return schedulersTransformer as ObservableTransformer<T, T>
    }


}