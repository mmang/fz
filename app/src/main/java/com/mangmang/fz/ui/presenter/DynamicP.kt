package com.mangmang.fz.ui.presenter

import com.mangmang.fz.base.BasePresenter
import com.mangmang.fz.ui.contract.DynamicContract
import com.mangmang.fz.utils.applySchedulers
import com.mangmang.fz.utils.showToast
import com.wzg.readbook.ui.contract.TestContract
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class DynamicP : BasePresenter<DynamicContract.V>, DynamicContract.P {

    override fun loadDynamicData(pageIndex: Int) {
        val queryMap = HashMap<String, String>()
        val params = HashMap<String, String>()
        queryMap.put("m", "feed/feed")
        queryMap.put("a", "getFeeds")
        queryMap.put("scope", "all")
        queryMap.put("pageSize", "25")
        queryMap.put("page", pageIndex.toString())

        apiService.loadDymaic(queryMap)
                .applySchedulers()
                .subscribe({
                    view?.setData(it)
                }, {

                })


    }

    @Inject
    constructor() : super()

}