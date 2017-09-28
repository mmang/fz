package com.mangmang.fz.ui.presenter

import android.content.Context
import com.mangmang.fz.base.BasePresenter
import com.mangmang.fz.bean.User
import com.mangmang.fz.ui.contract.LoginContract
import com.mangmang.fz.utils.MD5Util
import com.mangmang.fz.utils.UserManager
import com.wzg.readbook.ui.contract.TestContract
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
                .subscribe({
                    when (it.account_status) {
                        0 -> {
                            view?.showError("登录失败")
                        }
                        1 -> {
                            view?.loginScuess()
                            UserManager.storeAccount(userName, password, it.auth_token)
                            UserManager.user=it
                        }
                    }
                    view?.dismissLoading()
                }, {
                    view?.showError("登录出错")
                })
    }

    @Inject
    constructor() : super()


}