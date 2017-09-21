package com.mangmang.fz

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import com.mangmang.fz.base.BaseCommonActivity
import com.mangmang.fz.base.BasePresenter
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/14.
 * T : BaseContract.BaseV
 */
abstract class BaseActivity<T : BasePresenter<*>> : BaseCommonActivity(),
        BaseContract.BaseV {

    @Inject
    lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showLoading() {
        DialogManager.showLoadDialog(this)
    }

    override fun dismissLoading() {
        DialogManager.dissLoadDialog()
    }

    override fun getContext(): Context {
        return this
    }


}