package com.mangmang.fz

import android.content.Context

/**
 * Created by mangmang on 2017/8/29.
 */
interface BaseContract {

    interface BaseP {
        fun attachView(view: Any)
        fun loadData()
        fun detachView()

    }

    interface BaseV {
        fun showError(msg:String)
        fun complete()
        fun showLoading()
        fun dismissLoading()
        fun getContext(): Context
    }


}