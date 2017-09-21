package com.mangmang.fz.ui.contract

import com.mangmang.fz.BaseContract

/**
 * Created by mangmang on 2017/8/29.
 */

interface LoginContract {

    interface V : BaseContract.BaseV {
        fun loginScuess()
        fun loginFail()
    }

    interface P {
        fun login(name:String,password:String)
    }

}