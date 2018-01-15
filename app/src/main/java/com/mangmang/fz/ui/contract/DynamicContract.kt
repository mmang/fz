package com.mangmang.fz.ui.contract

import com.happyfi.lelerong.base.BaseContract
import com.mangmang.fz.bean.DynamicItem
import com.mangmang.fz.bean.PageBean

/**
 * Created by mangmang on 2017/8/29.
 * 动态 约束
 */
interface DynamicContract {

    interface V : BaseContract.BaseV {
        fun setData(dynamic: PageBean<DynamicItem>)
    }

    interface P {
        fun loadDynamicData(pageIndex: Int, uid: String)
    }
}