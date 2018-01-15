package com.happyfi.lelerong.base

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import com.mangmang.fz.DialogManager
import com.mangmang.fz.utils.showToast
import javax.inject.Inject


/**
 * Created by mangmang on 2017/9/14.
 * T : BaseContract.BaseV
 *
 * 需要p层的 继承这个
 *
 *  todo 重复实现了BaseContract.BaseV这个接口  <P : BasePresenter<*>> 这个*导致
 *  功能上是没有问题
 *
 *
 *
 */
abstract class BaseHasPActivity<P : BasePresenter<*>> : BaseCommonActivity(), BaseContract.BaseV {

    @Inject
    lateinit var presenter: P

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

    override fun showError(msg: String?) {
        showToast(msg)
    }

    override fun complete() {

    }
}