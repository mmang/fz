package com.happyfi.lelerong.base

import android.os.Bundle
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
abstract class HasPFragment<T : BasePresenter<*>> : BaseInjectorFragment() {
    @Inject lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}