package com.wzg.readbook.ui.contract

import com.happyfi.lelerong.base.BaseContract

/**
 * Created by mangmang on 2017/8/29.
 */

interface MainContract {

    interface V : BaseContract.BaseV {
        fun testV()
    }


    interface P : BaseContract.BaseP {
        fun testP()
    }


}