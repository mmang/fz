package com.mangmang.fz.utils.extension

import android.app.Activity
import android.content.Intent
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by  on 2017/12/6.
 */
inline fun <reified T : Activity> Activity.newIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}


inline fun <T> LifecycleProvider<ActivityEvent>.applySchedulers(): ObservableTransformer<T, T> {
    val schedulersTransformer = object : ObservableTransformer<Any, Any> {
        override fun apply(upstream: Observable<Any>?): ObservableSource<Any> {
            return upstream!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindUntilEvent(ActivityEvent.DESTROY))
        }
    }
    return schedulersTransformer as ObservableTransformer<T, T>
}

//fun <T> LifecycleProvider<ActivityEvent>.requestApi(observable: Observable<BaseResponse<T>>, baseObserver: BaseObserver<T>) {
//    observable.compose(applySchedulers())
//            .subscribe(baseObserver)
//}


inline fun <T> LifecycleProvider<FragmentEvent>.fargmentSchedulers(): ObservableTransformer<T, T> {
    val schedulersTransformer = object : ObservableTransformer<Any, Any> {
        override fun apply(upstream: Observable<Any>?): ObservableSource<Any> {
            return upstream!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindUntilEvent(FragmentEvent.DETACH))
        }
    }
    return schedulersTransformer as ObservableTransformer<T, T>
}

