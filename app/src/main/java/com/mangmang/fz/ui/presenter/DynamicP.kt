package com.mangmang.fz.ui.presenter

import android.text.TextUtils
import com.happyfi.lelerong.base.BasePresenter
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.ui.contract.DynamicContract
import com.mangmang.fz.utils.applySchedulers
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class DynamicP : BasePresenter<DynamicContract.V>, DynamicContract.P {

    override fun loadDynamicData(pageIndex: Int, uid: String) {
        if (TextUtils.isEmpty(uid)) {
            val queryMap = HashMap<String, String>()
            val params = HashMap<String, String>()
            queryMap.put("m", "feed/feed")
            queryMap.put("a", "getFeeds")
            queryMap.put("scope", "all")
            queryMap.put("pageSize", "25")
            queryMap.put("page", pageIndex.toString())
            apiService.loadDymaic(queryMap)
                    .applySchedulers()
                    .requestCallBack {
                        view?.setData(it.data!!)
                    }

        } else {
            //获取个人动态
            apiService.getStatusByUser(uid, pageIndex)
                    .applySchedulers()
                    .requestCallBack({
                        view?.setData(it.data!!)
                    }, { _, msg ->
                        view?.showError(msg)
                    })

        }


    }

    @Inject
    constructor() : super()


}