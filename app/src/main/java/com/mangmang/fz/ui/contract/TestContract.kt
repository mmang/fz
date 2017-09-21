package com.wzg.readbook.ui.contract

import com.mangmang.fz.BaseContract

/**
 * Created by mangmang on 2017/8/29.
 */

interface TestContract {

    interface V : BaseContract.BaseV {
        fun testV(string: String)
    }

    interface P {
        fun testP()
    }

}