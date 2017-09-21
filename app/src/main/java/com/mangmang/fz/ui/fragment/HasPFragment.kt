package com.mangmang.fz.ui.fragment

import android.os.Bundle
import com.mangmang.fz.BaseContract
import com.mangmang.fz.base.BasePresenter
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/19.
 */
abstract class HasPFragment<T : BasePresenter<*>> : BaseFragment() {
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