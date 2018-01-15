package com.mangmang.fz.ui.presenter

import com.happyfi.lelerong.base.BasePresenter
import com.wzg.readbook.ui.contract.TestContract
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class TestP : BasePresenter<TestContract.V>, TestContract.P {

    @Inject
    constructor() : super()

    override fun testP() {
        val queryMap = HashMap<String, String>()
        val params = HashMap<String, String>()
        queryMap.put("m", "account/account")
        queryMap.put("a", "login")
        params.put("authorization", "97faf8f61ca07a06f7bb999737158d4c")
        params.put("username", "610880568@qq.com")
        apiService.login(queryMap, params)
                .compose(applySchedulers())
                .subscribe({
                    view?.testV(it.toString())
                }, {

                })

    }


}

