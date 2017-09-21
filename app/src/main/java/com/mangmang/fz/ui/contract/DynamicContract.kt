package com.mangmang.fz.ui.contract

import com.mangmang.fz.BaseContract
import com.mangmang.fz.bean.Dynamic

/**
 * Created by mangmang on 2017/8/29.
 * 动态 约束
 */
interface DynamicContract {

    interface V : BaseContract.BaseV {
        fun setData(dynamic: Dynamic)
    }

    interface P {
        fun loadDynamicData(pageIndex: Int)
    }
}