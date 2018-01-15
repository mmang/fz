package com.mangmang.fz.ui.presenter

import com.happyfi.lelerong.base.BasePresenter
import com.mangmang.fz.net.requestCallBack
import com.mangmang.fz.ui.contract.LoginContract
import com.mangmang.fz.utils.MD5Util
import com.mangmang.fz.utils.UserManager
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by mangmang on 2017/9/14.
 */
class LoginP : BasePresenter<LoginContract.V>, LoginContract.P {

    override fun login(userName: String, password: String) {
        view?.showLoading()
        val password = MD5Util.md5Encode(password)
        val queryMap = HashMap<String, String>()
        val params = HashMap<String, String>()
        queryMap.put("a", "login")
        queryMap.put("m", "account/account")
        params.put("authorization", password)
        params.put("username", userName)
        apiService.login(queryMap, params)
                .compose(applySchedulers())
                .requestCallBack({
                    view?.loginScuess()
                    UserManager.storeAccount(userName, password, it.data!!.token)
                    UserManager.user = it.data
                    view?.dismissLoading()
                }, { _, _ ->
                    view?.dismissLoading()
                    view?.showError("登录失败")
                })
    }

    @Inject
    constructor() : super()


}


