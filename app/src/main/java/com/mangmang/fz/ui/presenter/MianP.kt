package com.mangmang.fz.ui.presenter

import com.happyfi.lelerong.base.BasePresenter
import com.wzg.readbook.ui.contract.MainContract
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class MianP : BasePresenter<MainContract.V>, MainContract.P {
    override fun testP() {

    }

    @Inject
    constructor() : super()

}